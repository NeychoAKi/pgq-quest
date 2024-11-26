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
public class AssignmentCompletion {

    /**
     * 任务Id
     */
    private Long taskId;

    /**
     * 任务反馈
     */
    private String feedback;

    /**
     * 作业乘积
     */
    private BigDecimal score;

    /**
     * 是否完成
     */
    private Boolean completed;
}
