package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.infrastructure.dao.po.SkillPO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@Mapper
public interface ISkillDao {

    /**
     * 保存Skill
     * @param skillPO 技能实体
     */
    void saveSkill(SkillPO skillPO);

    /**
     * 根据ID获得技能
     * @param skillId
     * @return
     */
    SkillPO getSkillById(Long skillId);

    List<Skill> getSkillListByUserAddress(String walletAddress);
}
