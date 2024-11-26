package tech.neychoup.domain.skill.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.task.model.aggregate.AssignmentCompletion;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.skill.service.ISkillService;

import java.io.IOException;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Service
public class SkillService implements ISkillService {


    @Autowired
    private ISkillRepository skillRepository;

    @Autowired
    private ITaskPort taskPort;

    @Override
    public SkillDetailAggregate generateTask(String skillName) {
        // 1. 存储技能并获得技能ID
        Skill skill = new Skill();
        skill.setName(skillName);

        // 2. 调用AI生成模块和任务
        SkillDetailAggregate skillDetailAggregate = taskPort.generateLearningTasks(skillName);

        // 3. 存储生成结果
        skillRepository.saveSkillTask(skillDetailAggregate);

        return skillDetailAggregate;
    }

    @Override
    public SkillDetailAggregate getSkillDetail(Long skillId) {
        return skillRepository.querySkillDetailBySkillId(skillId);
    }
}
