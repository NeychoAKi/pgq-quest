package tech.neychoup.domain.task.adapter.repository;

import tech.neychoup.domain.task.model.aggregate.TaskModule;
import tech.neychoup.domain.task.model.entity.Task;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description 任务仓储服务
 */
public interface ITaskRepository {

    /**
     * 存储模块化的任务
     * @param taskModuleList
     */
    void saveModularTask(List<TaskModule> taskModuleList);

    /**
     * 审查成功，更改任务完成状态
     */
    // void markTaskCompleted();

    /**
     * 根据SkillId查询模块化任务
     * @param skillId
     * @return
     */
    List<TaskModule> queryModularTask(Long skillId);

    Task queryTaskById(Long taskId);
}
