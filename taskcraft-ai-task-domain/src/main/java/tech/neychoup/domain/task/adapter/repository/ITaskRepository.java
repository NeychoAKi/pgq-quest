package tech.neychoup.domain.task.adapter.repository;

import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.domain.task.model.valobj.TaskCompletionVO;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
public interface ITaskRepository {

    void saveTaskCompletion(TaskCompletionVO taskCompletionVO, TaskCompletion completion);

    Task getTaskById(Long taskId);
}
