package tech.neychoup.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.skill.model.entity.Module;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.dao.*;
import tech.neychoup.infrastructure.dao.po.*;
import tech.neychoup.infrastructure.redis.RedissonService;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description Skill仓储实现
 */
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SkillRepository implements ISkillRepository {

    private final ITaskDao taskDao;

    private final IModuleDao moduleDao;

    private final ISkillDao skillDao;

    private final ITaskCompletionDao taskCompletionDao;

    private final RedissonService redissonService;

    /**
     * 保存技能和相关任务和任务模块
     *
     * @param skill 待保存技能数据
     * @return
     */
    @Override
    @Transactional
    public Skill saveSkill(Skill skill) {

        SkillPO skillPO = SkillPO.builder()
                .id(skill.getId())
                .walletAddress(skill.getWalletAddress())
                .name(skill.getName())
                .description(skill.getDescription())
                .build();

        // 1. 保存技能信息
        skillDao.saveSkill(skillPO);
        skill.setId(skillPO.getId());

        // 2. 封装Module和Task数据，存储模块和任务
        List<TaskPO> taskPOList = persistModulesAndCollectTasks(skill);

        // 3. 批量保存任务
        taskDao.insertTaskList(taskPOList);

        return skill;
    }

    /**
     * 持久化模块并收集任务列表
     *
     * @param skill
     * @return
     */
    private List<TaskPO> persistModulesAndCollectTasks(Skill skill) {
        List<TaskPO> taskPOList = new ArrayList<>();

        // 1. 获得模块列表
        skill.getModuleList().forEach(module -> {
            ModulePO modulePO = ModulePO.builder()
                    .skillId(skill.getId())
                    .id(module.getId())
                    .name(module.getName())
                    .objective(module.getObjective())
                    .isLocked(module.getIsLocked())
                    .unlockCost(module.getUnlockCost())
                    .build();

            // 1) 保存模块，获得moduleID
            moduleDao.saveModule(modulePO);

            // 2) 遍历模块中的任务列表，封装出任务PO列表
            module.getTaskList().forEach(task -> {
                TaskPO taskPO = TaskPO.builder()
                        .id(task.getId())
                        .moduleId(modulePO.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .difficulty(task.getDifficulty())
                        .tokenReward(task.getTokenReward())
                        .experienceReward(task.getExperienceReward())
                        .assignment(task.getAssignment())
                        .build();

                taskPOList.add(taskPO);
            });
        });

        return taskPOList;
    }

    @Override
    public Skill getSkillById(String walletAddress, Long skillId) {
        String cacheKey = "skill:" + skillId;

        // 尝试从缓存中获取技能信息
        Skill cachedSkill = redissonService.getValue(cacheKey);
        if (cachedSkill != null) {
            return cachedSkill;
        }

        // 1. 查询用户得技能信息
        SkillPO skillPO = skillDao.getSkillById(skillId);

        // 2. 查询模块和任务列表
        List<ModulePO> modulePOList = moduleDao.getModuleListBySkillId(skillId);
        List<TaskPO> taskPOList = fetchTasksByModule(modulePOList);

        // 3. 查询任务完成状态
        List<Long> taskIds = taskPOList.stream()
                .map(TaskPO::getId)
                .collect(Collectors.toList());
        List<TaskCompletionPO> completionList = Optional.ofNullable(
                taskCompletionDao.queryCompletionsByTaskIds(walletAddress, taskIds)
        ).orElse(Collections.emptyList());

        // 4. 构建任务完成状态 Map
        Map<Long, TaskCompletionPO> completionMap = completionList.stream()
                .collect(Collectors.toMap(TaskCompletionPO::getTaskId, Function.identity()));

        // 5. 构建模块和任务列表
        List<Module> moduleList = modulePOList.stream().map(modulePO -> {
            List<Task> taskList = taskPOList.stream()
                    .filter(taskPO -> taskPO.getModuleId().equals(modulePO.getId()))
                    .map(taskPO -> {
                        TaskCompletionPO completion = completionMap.get(taskPO.getId());

                        return Task.builder()
                                .id(taskPO.getId())
                                .name(taskPO.getName())
                                .description(taskPO.getDescription())
                                .difficulty(taskPO.getDifficulty())
                                .tokenReward(taskPO.getTokenReward())
                                .experienceReward(taskPO.getExperienceReward())
                                .assignment(taskPO.getAssignment())
                                .textContent(completion != null ? completion.getTextContent() : null)
                                .completed(completion != null ? completion.getCompleted() : null)
                                .failed(completion != null ? completion.getFailed() : null)
                                .feedback(completion != null ? completion.getFeedback() : null)
                                .score(completion != null ? completion.getScore() : null)
                                .build();
                    })
                    .collect(Collectors.toList());

            return Module.builder()
                    .id(modulePO.getId())
                    .name(modulePO.getName())
                    .objective(modulePO.getObjective())
                    .isLocked(modulePO.getIsLocked())
                    .unlockCost(modulePO.getUnlockCost())
                    .taskList(taskList)
                    .build();
        }).collect(Collectors.toList());

        // 6. 构建技能对象
        Skill skill = Skill.builder()
                .id(skillPO.getId())
                .name(skillPO.getName())
                .description(skillPO.getDescription())
                .moduleList(moduleList)
                .build();

        redissonService.setValue(cacheKey, skill, TimeUnit.MINUTES.toMinutes(20));

        return skill;
    }

    @Override
    public List<Skill> getSkillListByUserAddress(String walletAddress) {
        return skillDao.getSkillListByUserAddress(walletAddress);
    }

    /**
     * 获得模块下的所有任务
     *
     * @param modulePOList
     * @return
     */
    private List<TaskPO> fetchTasksByModule(List<ModulePO> modulePOList) {
        List<TaskPO> taskPOList = new ArrayList<>();
        modulePOList.forEach(modulePO -> {
            List<TaskPO> moduleTasks = taskDao.queryTasksByModuleId(modulePO.getId());
            taskPOList.addAll(moduleTasks);
        });
        return taskPOList;
    }
}
