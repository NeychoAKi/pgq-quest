package tech.neychoup.domain.task.service.impl;

import org.springframework.stereotype.Service;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.aggregate.TaskModule;
import tech.neychoup.domain.task.model.aggregate.TaskCompletion;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.service.ITaskService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Service
public class TaskService implements ITaskService {

    @Resource
    private ITaskRepository taskRepository;

    @Resource
    private ITaskPort taskPort;

    @Override
    public List<TaskModule> generateTask(String skill) {
        List<TaskModule> taskModuleList = taskPort.generateLearningTasks(skill);
        if (taskModuleList != null) {
            taskRepository.saveModularTask(taskModuleList);
        }
        return taskModuleList;
    }

    @Override
    public TaskCompletion verifyTaskAssignment(Long taskId, String textContent, String imageUrl) {

        TaskCompletion taskCompletion;
        try {
            Task task = taskRepository.queryTaskById(taskId);
            taskCompletion = taskPort.verifyTaskFinished(task, textContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskCompletion;
    }

    @Override
    public List<TaskModule> queryModularTask(Long skillId) {
        return taskRepository.queryModularTask(skillId);
    }

    @Override
    public Task queryTaskById(Long taskId) {
        Task task = taskRepository.queryTaskById(taskId);
        return task;
    }
}
