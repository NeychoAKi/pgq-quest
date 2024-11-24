package tech.neychoup.domain.task.model.aggregate;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 任务完成情况
 */
@Data
public class TaskCompletion {

    /**
     *
     */
    private Boolean isCompletion;

    /**
     * 任务反馈
     */
    private String feedback;

    /**
     *
     */
    private BigDecimal score;

}
