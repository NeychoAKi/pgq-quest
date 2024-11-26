package tech.neychoup.domain.task.service;

import tech.neychoup.domain.task.model.aggregate.AssignmentCompletion;
import tech.neychoup.domain.task.model.entity.Task;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ITaskService {

    /**
     * 验收作业情况，并返回完成情况
     * @param taskId
     * @param textContent
     * @param imageUrl
     * @return
     */
    AssignmentCompletion verifyTaskAssignment(Long taskId, String textContent, String imageUrl);

    /**
     * 根据任务Id返回任务
     * @param taskId
     * @return
     */
    Task queryTaskById(Long taskId);
}
