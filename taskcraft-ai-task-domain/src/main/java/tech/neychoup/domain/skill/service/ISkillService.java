package tech.neychoup.domain.skill.service;

import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
public interface ISkillService {

    /**
     * 根据技能名称生成任务
     * @param skillName
     * @return
     */
    SkillDetailAggregate generateTask(String skillName);

    /**
     * 根据技能ID查询任务详情
     * @param skillId
     * @return
     */
    SkillDetailAggregate getSkillDetail(Long skillId);
}
