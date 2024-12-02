package tech.neychoup.domain.task.service;

import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.domain.task.model.valobj.TaskCompletionVO;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ITaskService {

    /**
     * 验收作业情况，并返回完成情况
     *
     * @param taskCompletionVO 作业完成信息
     * @return
     */
    TaskCompletion verifyTaskAssignment(TaskCompletionVO taskCompletionVO);

    /**
     * 根据任务Id返回任务
     * @param taskId
     * @return
     */
    Task queryTaskById(Long taskId);
}
