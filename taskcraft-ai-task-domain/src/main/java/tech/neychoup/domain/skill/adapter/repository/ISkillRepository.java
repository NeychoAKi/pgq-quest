package tech.neychoup.domain.skill.adapter.repository;

import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ISkillRepository {

    void saveSkillTask(SkillDetailAggregate skillDetailAggregate);

    SkillDetailAggregate querySkillDetailBySkillId(Long skillId);
}
