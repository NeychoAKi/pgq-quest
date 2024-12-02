package tech.neychoup.domain.task.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description
 */
@Data
@Builder
@ToString
public class Task implements Serializable {

    /**
     * 任务ID
     */
    private Long id;

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
     * 经验值奖励
     */
    private Integer experienceReward;

    /**
     * 作业
     */
    private String assignment;

    /**
     * 是否完成
     */
    private Boolean completed;

    /**
     * 是否失败
     */
    private Boolean failed;

    /**
     * 作业内容
     */
    private String textContent;

    /**
     * 作业成绩
     */
    private Integer score;

    /**
     * 任务反馈
     */
    private String feedback;
}
