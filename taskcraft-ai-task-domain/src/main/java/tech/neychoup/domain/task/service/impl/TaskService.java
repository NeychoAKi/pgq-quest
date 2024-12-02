package tech.neychoup.domain.task.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.domain.task.model.valobj.TaskCompletionVO;
import tech.neychoup.domain.task.service.ITaskService;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService implements ITaskService {

    private final ITaskPort taskPort;

    private final ITaskRepository taskRepository;

    @Override
    public TaskCompletion verifyTaskAssignment(TaskCompletionVO taskCompletionVO) {
        // 1. 获得任务
        Task task = taskRepository.getTaskById(taskCompletionVO.getTaskId());

        // 2. 验证任务完成情况
        TaskCompletion completion = taskPort.verifyTaskFinished(task, taskCompletionVO.getTextContent());

        // 3. 保存任务完成情况
        if (completion == null) {
            throw new RuntimeException("不存在验证任务");
        }
        taskRepository.saveTaskCompletion(taskCompletionVO, completion);

        return completion;
    }

    @Override
    public Task queryTaskById(Long taskId) {
        return taskRepository.getTaskById(taskId);
    }
}
