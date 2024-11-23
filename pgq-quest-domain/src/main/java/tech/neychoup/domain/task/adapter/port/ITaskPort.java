package tech.neychoup.domain.task.adapter.port;

import tech.neychoup.domain.task.model.aggregate.Module;
import tech.neychoup.domain.task.model.entity.Task;

import java.io.IOException;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description
 */
public interface ITaskPort {

    /**
     * 生成成长式任务
     * @param topic 主题
     * @return 按模块划分的任务
     */
    List<Module> generateLearningTasks(String topic);


    Boolean verifyTaskFinished(Task task, String content) throws IOException;
}
