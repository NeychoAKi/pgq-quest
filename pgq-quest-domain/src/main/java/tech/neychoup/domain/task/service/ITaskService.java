package tech.neychoup.domain.task.service;

import tech.neychoup.domain.task.model.aggregate.Module;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
public interface ITaskService {

    void saveGeneratedTask(List<Module> moduleList);
}
