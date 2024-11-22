package tech.neychoup.domain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description
 */
@Data
public class Task implements Serializable {

    private String id;
    private Long skillId;
    @JsonProperty("taskName")
    private String taskName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("difficulty")
    private Long difficulty;
    @JsonProperty("tokenReward")
    private Long tokenReward;
    @JsonProperty("experienceReward")
    private Long experienceReward;
    @JsonProperty("assignment")
    private String assignment;
}
