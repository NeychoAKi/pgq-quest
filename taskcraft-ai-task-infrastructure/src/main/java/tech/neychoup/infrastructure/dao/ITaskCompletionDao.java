package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import tech.neychoup.infrastructure.dao.po.TaskCompletionPO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Mapper
public interface ITaskCompletionDao {

    /**
     * 保存任务完成情况
     * @param taskCompletion 任务完成情况PO
     */
    void saveTaskCompletion(TaskCompletionPO taskCompletion);

    TaskCompletionPO queryTaskCompletionById(String walletAddress, Long taskId);

    void updateTaskCompletion(TaskCompletionPO savedTaskCompletion);

    List<TaskCompletionPO> queryCompletionsByTaskIds(String walletAddress, List<Long> taskIds);
}
