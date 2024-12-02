package tech.neychoup.infrastructure.gateway;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.task.model.entity.TaskCompletion;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 任务助手
 */

public interface TaskAssistant {

    /**
     * 生成学习任务
     *
     * @param skillTopic 学习主题
     * @return
     */
    @SystemMessage("你是一位专业的{{topic}}导师。请根据以下要求生成学习任务：\n" +
            "1. 将{{topic}}的学习内容模块化，每个模块包含多个子任务。\n" +
            "2. 每个模块有明确的学习目标。\n" +
            "3. 每个子任务需要提供任务描述、难度系数、代币奖励和经验值奖励。\n" +
            "4. 经验值奖励应遵循递进式设计，难度越高奖励越多，最高为5级，且最初等级都为1。\n" +
            "5. 每个子任务需要提供具体的作业要求。\n" +
            "6. 展示从基础到高级的完整学习路径。\n" +
            "7. 输出结果以压缩的JSON格式呈现\n" +
            "注意：JSON格式不需要换行或使用代码块修饰，输出内容必须严格按照以上示例格式。确保生成内容清晰准确。")
    @UserMessage("生成关于{{topic}}的中文学习任务")
    Skill generateTask(@V("topic") String skillTopic);

    /**
     * 审查任务完成情况
     *
     * @param content
     * @return
     */
    @SystemMessage("你是一位专业的任务审核员。请根据以下要求审核用户提交的作业：\n" +
            "1. 审核用户的提交内容是否符合作业要求。\n" +
            "2. 提供完成度评分，满分100分。80分以上才算通过\n" +
            "3. 如果未完成或部分完成，请具体说明用户未满足的部分。")
    @UserMessage("任务描述：{{description}}\n作业要求：{{assignment}}\n用户提交内容：{{content}}")
    TaskCompletion verifyTaskCompleted(
            @V("description") String description,
            @V("assignment") String assignment,
            @V("content") String content);
}
