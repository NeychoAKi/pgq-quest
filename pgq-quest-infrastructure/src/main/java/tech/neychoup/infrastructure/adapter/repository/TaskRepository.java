package tech.neychoup.infrastructure.adapter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.aggregate.TaskModule;
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
    public void saveModularTask(List<TaskModule> taskModuleList) {
        List<ModulePO> modulePOList = new ArrayList<>();
        for (TaskModule taskModule : taskModuleList) {
            ModulePO modulePO = new ModulePO();
            modulePO.setSkillId(1L); // mock
            modulePO.setModuleName(taskModule.getModuleName());
            modulePO.setObjective(taskModule.getObjective());
            modulePOList.add(modulePO);
        }

        moduleDao.insertModules(modulePOList);

        List<TaskPO> allTaskPOs = new ArrayList<>();
        for (int i = 0; i < taskModuleList.size(); i++) {
            TaskModule taskModule = taskModuleList.get(i);
            ModulePO modulePO = modulePOList.get(i);
            Long moduleId = modulePO.getId();

            List<Task> tasks = taskModule.getTasks();
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

    @Override
    public List<TaskModule> queryModularTask(Long skillId) {

        List<ModulePO> modulePOList = moduleDao.queryModulesBySkillId(skillId);

        List<TaskModule> taskModules = new ArrayList<>();

        for (ModulePO modulePO : modulePOList) {
            TaskModule taskModule = new TaskModule();
            taskModule.setModuleName(modulePO.getModuleName());
            taskModule.setObjective(modulePO.getObjective());

            List<TaskPO> taskPOList = taskDao.queryTasksByModuleId(modulePO.getId());

            List<Task> tasks = new ArrayList<>();

            for (TaskPO taskPO : taskPOList) {
                Task task = new Task();
                task.setId(taskPO.getId());
                task.setTaskName(taskPO.getTaskName());
                task.setDescription(taskPO.getDescription());
                task.setDifficulty(taskPO.getDifficulty());
                task.setTokenReward(taskPO.getTokenReward());
                task.setExperienceReward(taskPO.getExperienceReward());
                task.setAssignment(taskPO.getAssignment());
                tasks.add(task);
            }

            taskModule.setTasks(tasks);

            taskModules.add(taskModule);
        }
        return taskModules;
    }

    @Override
    public Task queryTaskById(Long taskId) {
        return taskDao.queryTaskById(taskId);
    }
}
