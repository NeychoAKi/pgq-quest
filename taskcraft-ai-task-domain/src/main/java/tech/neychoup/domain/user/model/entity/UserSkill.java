package tech.neychoup.domain.user.model.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 用户技能
 */
@Data
@Builder
public class UserSkill {

    private Long skillId;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
    private String description;

    /**
     * 技能等级
     */
    private Integer level;

    /**
     * 技能经验
     */
    private Integer experience;

    /**
     * 技能进度
     */
    private Integer progress;

}
