package tech.neychoup.domain.skill.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.skill.service.ISkillService;

import java.util.List;
import java.util.concurrent.Executors;


/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Slf4j
@Service
public class SkillService implements ISkillService {

    @Autowired
    private ISkillRepository skillRepository;

    @Autowired
    private ITaskPort taskPort;

    @Override
    public SkillDetailAggregate generateTask(String skillName) {
        // 1. 调用AI生成模块和任务
        SkillDetailAggregate skillDetailAggregate = taskPort.generateLearningTasks(skillName);
        skillDetailAggregate.getSkill().setName(skillName);

        // 2. 异步存储生成结果
        saveSkillTaskAsync(skillDetailAggregate);

        return skillDetailAggregate;
    }

    /**
     * 异步保存 Skill 和相关任务和任务模块
     * @param skillDetailAggregate
     */
    private void saveSkillTaskAsync(SkillDetailAggregate skillDetailAggregate) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                try {
                    skillRepository.saveSkillTask(skillDetailAggregate);
                } catch (RuntimeException e) {
                    log.error("Failed to save skill task", e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            // 捕获线程池创建或提交任务的异常
            throw new RuntimeException("Failed to execute saveSkillTaskAsync", e);
        }
    }

    @Override
    public SkillDetailAggregate getSkillDetail(Long skillId) {
        return skillRepository.querySkillDetailBySkillId(skillId);
    }
}
