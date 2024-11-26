package tech.neychoup.domain.skill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.neychoup.domain.task.model.entity.Task;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    /**
     * 模块ID
     */
    private Long id;

    /**
     * 模块名称
     */
    private String name;

    /**
     * 目标
     */
    private String objective;

    /**
     * 是否被锁
     */
    private Boolean isLocked;

    /**
     * 解锁所需花费
     */
    private Integer unlockCost;

    /**
     * 任务列表
     */
    private List<Task> tasks;
}
