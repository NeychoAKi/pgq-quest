package tech.neychoup.domain.task.adapter.repository;

import tech.neychoup.domain.task.model.aggregate.Module;

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
     * @param moduleList
     */
    void saveModularTask(List<Module> moduleList);
}
