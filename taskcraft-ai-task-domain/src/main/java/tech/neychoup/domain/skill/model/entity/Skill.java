package tech.neychoup.domain.skill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 技能实体对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill {

    /**
     * 技能ID
     */
    private Long id;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能等级
     */
    private int level;
    /**
     * 当前经验值
     */
    private int experience;
}
