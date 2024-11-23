package tech.neychoup.domain.task.service.impl;

import org.springframework.stereotype.Service;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.aggregate.Module;
import tech.neychoup.domain.task.service.ITaskService;

import javax.annotation.Resource;
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

    @Override
    public void saveGeneratedTask(List<Module> moduleList) {
        taskRepository.saveModularTask(moduleList);
    }
}
