package tech.neychoup.domain.task.model.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
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
@ToString
public class Module implements Serializable {

    @JsonProperty("moduleName")
    private String moduleName;
    @JsonProperty("objective")
    private String objective;
    @JsonProperty("tasks")
    private List<Task> tasks;
}
