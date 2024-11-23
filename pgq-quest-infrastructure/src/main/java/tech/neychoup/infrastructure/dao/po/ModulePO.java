package tech.neychoup.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 任务模块
 */
@Data
public class ModulePO {

    private Long id;

    /**
     * 归属技能
     */
    private Long skillId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块目标
     */
    private String objective;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
