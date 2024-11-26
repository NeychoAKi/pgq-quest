package tech.neychoup.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@Data
public class SkillPO {

    /**
     * 技能ID
     */
    private Long id;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
