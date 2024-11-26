package tech.neychoup.domain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@ToString
public class Task implements Serializable {

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 技能ID
     */
    private Long skillId;

    /**
     * 任务名称
     */
    @JsonProperty("taskName")
    private String taskName;

    /**
     * 任务描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 任务难度
     */
    @JsonProperty("difficulty")
    private Integer difficulty;

    /**
     * 代币奖励
     */
    @JsonProperty("tokenReward")
    private Long tokenReward;

    /**
     * 经验值奖励
     */
    @JsonProperty("experienceReward")
    private Long experienceReward;

    /**
     * 作业
     */
    @JsonProperty("assignment")
    private String assignment;
}
