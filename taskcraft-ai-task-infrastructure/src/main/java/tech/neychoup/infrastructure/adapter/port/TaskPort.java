package tech.neychoup.infrastructure.adapter.port;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.gateway.TaskAssistant;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.infrastructure.redis.RedissonService;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description AI任务接口
 */
@Service
public class TaskPort implements ITaskPort {

    @Autowired
    private TaskAssistant taskAssistant;

    @Autowired
    private RedissonService redissonService;

    /**
     * 根据技能主题生成任务
     * @param skillTopic 技能主题
     * @return
     */
    @Override
    public Skill generateSkillTask(String skillTopic) {
        try {
            // 无资源锁
            RLock lock = redissonService.getLock("generate_skill_lock_".concat(skillTopic));
            try {
                lock.lock();
                // 1. 执行AI生成任务
                Skill skill = taskAssistant.generateTask(skillTopic);
                return skill;
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate skill by LLM", e);
        }
    }

    /**
     * 验证任务完成情况
     *
     * @param task     任务
     * @param solution 用户提交的解决方案
     * @return 返回完成情况
     */
    @Override
    public TaskCompletion verifyTaskFinished(Task task, String solution) {
        // 1. 执行AI验证任务完成情况
        TaskCompletion taskCompletion = taskAssistant.verifyTaskCompleted(task.getDescription(), task.getAssignment(), solution);

        return taskCompletion;
    }
}
