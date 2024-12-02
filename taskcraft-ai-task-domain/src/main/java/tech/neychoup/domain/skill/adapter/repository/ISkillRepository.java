package tech.neychoup.domain.skill.adapter.repository;


import tech.neychoup.domain.skill.model.entity.Skill;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ISkillRepository {

    /**
     * 保存技能
     * @param skill
     * @return
     */
    Skill saveSkill(Skill skill);

    /**
     * 根据ID获得技能
     * @param walletAddress
     * @param skillId
     * @return
     */
    Skill getSkillById(String walletAddress, Long skillId);

    /**
     * 根据用户地址获得技能
     * @param walletAddress
     * @return
     */
    List<Skill> getSkillListByUserAddress(String walletAddress);
}
