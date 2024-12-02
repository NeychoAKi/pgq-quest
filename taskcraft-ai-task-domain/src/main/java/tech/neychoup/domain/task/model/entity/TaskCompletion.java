package tech.neychoup.domain.task.model.entity;

import lombok.Data;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-28
 * @description
 */
@Data
public class TaskCompletion {

    /**
     * 提交内容（文本内容、图片内容）
     */
    private String text_content;
    private String image_url;

    /**
     * 任务反馈
     */
    private String feedback;

    /**
     * 作业分数
     */
    private Integer score;

    /**
     * 是否完成
     */
    private Boolean completed;

    /**
     * 是否失败
     */
    private Boolean failed;

}
