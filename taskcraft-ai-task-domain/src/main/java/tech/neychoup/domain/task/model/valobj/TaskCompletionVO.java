package tech.neychoup.domain.task.model.valobj;

import lombok.Data;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 任务完成请求VO对象
 */
@Data
public class TaskCompletionVO {

    private String walletAddress;

    private Long taskId;

    private String textContent;

    private String imgUrl;

}
