package tech.neychoup.domain.skill.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.skill.service.ISkillService;

import java.util.List;


/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SkillService implements ISkillService {

    private final ISkillRepository skillRepository;

    private final ITaskPort taskPort;

    @Override
    public Skill generateSkill(String walletAddress, String skillTopic) {

        // 1. 调用AI生成模块和任务
        Skill skill = generateSkillFromAI(skillTopic);
        log.info("Generated skill successfully: {}", skill);

        // 设置用户技能参数(避免生成的技能名称与技能主题不同)
        skill.setName(skillTopic);
        skill.setWalletAddress(walletAddress);

        // 2. 存储（需要ID => 同步）
        skillRepository.saveSkill(skill);
        log.info("Skill saved successfully!");

        return skill;
    }

    /**
     * 调用AI生成技能（包括模块、任务）
     *
     * @param skillTopic
     * @return
     */
    private Skill generateSkillFromAI(String skillTopic) {
        log.info("Generated skill for topic: {}", skillTopic);
        return taskPort.generateSkillTask(skillTopic);
    }

    @Override
    public Skill getSkillById(String walletAddress, Long id) {
        log.info("Get skill by id: {}", id);
        return skillRepository.getSkillById(walletAddress, id);
    }

    @Override
    public List<Skill> getSkillListByUserAddress(String walletAddress) {
        log.info("Get skills by user address: {}", walletAddress);
        return skillRepository.getSkillListByUserAddress(walletAddress);
    }
}
