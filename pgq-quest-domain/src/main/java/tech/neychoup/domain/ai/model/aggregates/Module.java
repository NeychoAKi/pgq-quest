package tech.neychoup.domain.ai.model.aggregates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tech.neychoup.domain.task.model.entity.Task;

import java.io.Serializable;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description
 */
@Data
public class Module implements Serializable {

    @JsonProperty("moduleName")
    private String moduleName;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("tasks")
    private List<Task> tasks;
}
