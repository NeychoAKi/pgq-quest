package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.dao.po.TaskPO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description Task 存储服务
 */
@Mapper
public interface ITaskDao {
    void insertTaskList(@Param("tasks") List<TaskPO> tasks);

    List<TaskPO> queryTasksByModuleId(Long id);

    Task queryTaskById(Long taskId);
}
