package tech.neychoup.domain.skill.service;

import tech.neychoup.domain.skill.model.entity.Skill;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
public interface ISkillService {

    /**
     * 根据技能名称生成任务
     *
     * @param skillTopic 技能主题
     * @return
     */
    Skill generateSkill(String walletAddress, String skillTopic);

    /**
     * 根据技能ID查询技能
     * @param walletAddress
     * @param id
     * @return
     */
    Skill getSkillById(String walletAddress, Long id);

    /**
     * 获得用户所有技能
     *
     * @param walletAddress
     * @return
     */
    List<Skill> getSkillListByUserAddress(String walletAddress);
}
