package tech.neychoup.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.neychoup.domain.task.adapter.repository.ITaskRepository;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.valobj.TaskCompletionVO;
import tech.neychoup.infrastructure.dao.ITaskCompletionDao;
import tech.neychoup.infrastructure.dao.ITaskDao;
import tech.neychoup.infrastructure.dao.po.TaskCompletionPO;
import tech.neychoup.infrastructure.dao.po.TaskPO;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.infrastructure.redis.RedissonService;

import java.util.concurrent.TimeUnit;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskRepository implements ITaskRepository {

    private final ITaskDao taskDao;

    private final ITaskCompletionDao taskCompletionDao;

    private final RedissonService redissonService;

    @Override
    public void saveTaskCompletion(TaskCompletionVO taskCompletionVO, TaskCompletion assignmentCompletion) {

        TaskCompletionPO savedTaskCompletion = taskCompletionDao.queryTaskCompletionById(
                taskCompletionVO.getWalletAddress(),
                taskCompletionVO.getTaskId());

        if (savedTaskCompletion != null) {
            // 如果记录已存在，并且任务已完成，抛出异常
            if (!savedTaskCompletion.getFailed()) {
                throw new RuntimeException("Task already completed!");
            }
            // 更新已存在的记录
            savedTaskCompletion.setTextContent(taskCompletionVO.getTextContent());
            savedTaskCompletion.setScore(assignmentCompletion.getScore());
            savedTaskCompletion.setFeedback(assignmentCompletion.getFeedback());
            savedTaskCompletion.setCompleted(assignmentCompletion.getCompleted());
            savedTaskCompletion.setFailed(assignmentCompletion.getFailed());

            taskCompletionDao.updateTaskCompletion(savedTaskCompletion);
        } else {
            // 将TaskCompletionVO转换为TaskCompletionPO
            TaskCompletionPO taskCompletionPO = TaskCompletionPO.builder()
                    .taskId(taskCompletionVO.getTaskId())
                    .walletAddress(taskCompletionVO.getWalletAddress())
                    .imageUrl(taskCompletionVO.getImgUrl())
                    .textContent(taskCompletionVO.getTextContent())
                    .completed(assignmentCompletion.getCompleted())
                    .failed(assignmentCompletion.getFailed())
                    .score(assignmentCompletion.getScore())
                    .feedback(assignmentCompletion.getFeedback())
                    .build();

            taskCompletionDao.saveTaskCompletion(taskCompletionPO);
        }

    }

    @Override
    public Task getTaskById(Long taskId) {

        Task task = redissonService.getValue("task:" +taskId);
        if (task != null) {
            return task;
        }

        // 调用DAO层查询任务
        TaskPO taskPO = taskDao.queryTaskById(taskId);
        if (taskPO == null) {
            return null;
        }
        // 将TaskPO转换为Task
        task = Task.builder()
                .id(taskPO.getId())
                .name(taskPO.getName())
                .description(taskPO.getDescription())
                .difficulty(taskPO.getDifficulty())
                .tokenReward(taskPO.getTokenReward())
                .experienceReward(taskPO.getExperienceReward())
                .assignment(taskPO.getAssignment())
                .build();

        redissonService.setValue("task:" + taskId, task, TimeUnit.MINUTES.toMinutes(20));
        return task;
    }
}
