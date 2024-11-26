package tech.neychoup.infrastructure.adapter.port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.aggregate.AssignmentCompletion;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.gateway.TaskAssistant;

import java.io.IOException;

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

    @Override
    public SkillDetailAggregate generateLearningTasks(String skillName) {
        try {
            // 1. 执行AI生成任务
            SkillDetailAggregate skillDetail = taskAssistant.generateTask(skillName);

            return skillDetail;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call ChatGLM", e);
        }
    }

    @Override
    public AssignmentCompletion verifyTaskFinished(Task task, String content) throws IOException {
        AssignmentCompletion assignmentCompletion = taskAssistant.verifyTaskCompleted(task.getDescription(), task.getAssignment(), content);

        return assignmentCompletion;
    }
}
