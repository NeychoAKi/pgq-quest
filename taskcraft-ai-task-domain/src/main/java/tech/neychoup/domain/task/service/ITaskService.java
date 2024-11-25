package tech.neychoup.domain.task.service;

import tech.neychoup.domain.task.model.aggregate.TaskModule;
import tech.neychoup.domain.task.model.aggregate.TaskCompletion;
import tech.neychoup.domain.task.model.entity.Task;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
public interface ITaskService {

    List<TaskModule> generateTask(String skill);
    TaskCompletion verifyTaskAssignment(Long taskId, String textContent, String imageUrl);
    List<TaskModule> queryModularTask(Long skillId);
    Task queryTaskById(Long taskId);
}
