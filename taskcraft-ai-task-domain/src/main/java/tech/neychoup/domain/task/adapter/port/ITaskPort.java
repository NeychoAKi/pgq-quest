package tech.neychoup.domain.task.adapter.port;

import tech.neychoup.domain.skill.model.entity.Skill;

import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.entity.TaskCompletion;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description
 */
public interface ITaskPort {

    /**
     * 生成成长式任务
     *
     * @param skillTopic 技能主题
     * @return 按模块划分的任务
     */
    Skill generateSkillTask(String skillTopic);


    TaskCompletion verifyTaskFinished(Task task, String solution);
}
