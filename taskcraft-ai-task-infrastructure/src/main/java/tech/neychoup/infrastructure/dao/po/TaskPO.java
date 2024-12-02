package tech.neychoup.infrastructure.dao.po;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 任务
 */
@Data
@Builder
public class TaskPO {

    private Long id;

    /**
     * 归属模块
     */
    private Long moduleId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务难度
     */
    private Integer difficulty;

    /**
     * 代币奖励
     */
    private Double tokenReward;

    /**
     * 经验奖励
     */
    private Integer experienceReward;

    /**
     * 任务作业
     */
    private String assignment;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
