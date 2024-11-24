package tech.neychoup.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 任务完成情况响应DTO
 */
@Data
@Builder
public class TaskAssignmentResponseDTO {

    /**
     * 任务提交反馈
     */
    String feedback;

    /**
     * 任务完成分数
     */
    BigDecimal score;

    /**
     * 是否完成
     */
    Boolean isCompletion;
}
