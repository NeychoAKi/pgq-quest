package tech.neychoup.domain.skill.adapter.repository;

import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.skill.model.entity.Skill;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ISkillRepository {

    void saveSkillTask(SkillDetailAggregate skillDetailAggregate);
}
