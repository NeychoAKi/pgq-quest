package tech.neychoup.api.dto;

import lombok.Data;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 用户提交作业请求DTO
 */
@Data
public class TaskAssignmentRequestDTO {


    private Long taskId;

    /**
     * 文字内容
     */
    private String textContent;

    /**
     * 图片
     */
    private String imageUrl;
}
