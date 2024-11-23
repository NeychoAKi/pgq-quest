package tech.neychoup.infrastructure.adapter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.aggregate.Module;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.dao.IModuleDao;
import tech.neychoup.infrastructure.dao.ITaskDao;
import tech.neychoup.infrastructure.dao.po.ModulePO;
import tech.neychoup.infrastructure.dao.po.TaskPO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description Task 仓储实现
 */
@Repository
public class TaskRepository implements ITaskRepository {

    @Resource
    private ITaskDao taskDao;

    @Resource
    private IModuleDao moduleDao;

    @Override
    @Transactional
    public void saveModularTask(List<Module> moduleList) {
        List<ModulePO> modulePOList = new ArrayList<>();
        for (Module module : moduleList) {
            ModulePO modulePO = new ModulePO();
            modulePO.setSkillId(1L); // mock
            modulePO.setModuleName(module.getModuleName());
            modulePO.setObjective(module.getObjective());
            modulePOList.add(modulePO);
        }

        moduleDao.insertModules(modulePOList);

        List<TaskPO> allTaskPOs = new ArrayList<>();
        for (int i = 0; i < moduleList.size(); i++) {
            Module module = moduleList.get(i);
            ModulePO modulePO = modulePOList.get(i);
            Long moduleId = modulePO.getId();

            List<Task> tasks = module.getTasks();
            if (tasks != null) {
                for (Task task : tasks) {
                    TaskPO taskPO = new TaskPO();
                    taskPO.setId(task.getId());
                    taskPO.setModuleId(moduleId);
                    taskPO.setTaskName(task.getTaskName());
                    taskPO.setDescription(task.getDescription());
                    taskPO.setDifficulty(task.getDifficulty());
                    taskPO.setTokenReward(task.getTokenReward());
                    taskPO.setExperienceReward(task.getExperienceReward());
                    taskPO.setAssignment(task.getAssignment());
                    allTaskPOs.add(taskPO);
                }
            }
        }

        if (!allTaskPOs.isEmpty()) {
            taskDao.insertTaskList(allTaskPOs);
        }
    }
}
