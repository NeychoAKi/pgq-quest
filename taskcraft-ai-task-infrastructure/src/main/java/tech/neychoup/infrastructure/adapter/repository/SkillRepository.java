package tech.neychoup.infrastructure.adapter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.skill.model.entity.Module;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.infrastructure.dao.IModuleDao;
import tech.neychoup.infrastructure.dao.ISkillDao;
import tech.neychoup.infrastructure.dao.ITaskDao;
import tech.neychoup.infrastructure.dao.IUserSkillDao;
import tech.neychoup.infrastructure.dao.po.ModulePO;
import tech.neychoup.infrastructure.dao.po.SkillPO;
import tech.neychoup.infrastructure.dao.po.TaskPO;
import tech.neychoup.infrastructure.dao.po.UserSkillPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description Skill 仓储实现
 */
@Repository
public class SkillRepository implements ISkillRepository {

    @Autowired
    private ITaskDao taskDao;

    @Autowired
    private IModuleDao moduleDao;

    @Autowired
    private ISkillDao skillDao;

    @Autowired
    private IUserSkillDao userSkillDao;

    @Override
    @Transactional
    public void saveSkillTask(SkillDetailAggregate skillDetailAggregate) {
        Skill skill = skillDetailAggregate.getSkill();
        SkillPO skillPO = new SkillPO();
        skillPO.setName(skill.getName());
        skillPO.setDescription(skill.getDescription());
        skillDao.saveSkill(skillPO);

        userSkillDao.saveUserSkill(new UserSkillPO(skillDetailAggregate.getWalletAddress(), skillPO.getId(), 0, 0, 0, new Date()));

        List<Module> moduleList = skillDetailAggregate.getModuleList();
        List<TaskPO> taskPOList = new ArrayList<>();

        moduleList.forEach(module -> {
            ModulePO modulePO = new ModulePO();
            modulePO.setSkillId(skillPO.getId());
            modulePO.setModuleName(module.getName());
            modulePO.setObjective(module.getObjective());
            modulePO.setIsLocked(module.getIsLocked());
            modulePO.setUnlockCost(module.getUnlockCost());

            moduleDao.saveModule(modulePO);

            module.getTasks().forEach(task -> {
                TaskPO taskPO = new TaskPO();
                taskPO.setId(task.getId());
                taskPO.setSkillId(skillPO.getId());
                taskPO.setModuleId(modulePO.getId());
                taskPO.setTaskName(task.getTaskName());
                taskPO.setDescription(task.getDescription());
                taskPO.setDifficulty(task.getDifficulty());
                taskPO.setTokenReward(task.getTokenReward());
                taskPO.setExperienceReward(task.getExperienceReward());
                taskPO.setAssignment(task.getAssignment());
                taskPO.setIsCompleted(false);

                taskPOList.add(taskPO);
            });
        });

        // 批量保存任务
        taskDao.insertTaskList(taskPOList);
    }
}
