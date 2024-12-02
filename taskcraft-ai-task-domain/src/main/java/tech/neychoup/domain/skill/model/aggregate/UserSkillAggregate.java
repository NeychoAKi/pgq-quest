package tech.neychoup.domain.skill.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.neychoup.domain.skill.model.entity.Skill;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 用户技能聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillAggregate {

    private String walletAddress;

    /**
     * 技能
     */
    private Skill skill;
}
