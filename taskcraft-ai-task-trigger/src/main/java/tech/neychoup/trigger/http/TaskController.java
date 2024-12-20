package tech.neychoup.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.neychoup.api.dto.TaskAssignmentRequestDTO;
import tech.neychoup.api.dto.TaskAssignmentResponseDTO;
import tech.neychoup.api.response.Response;
import tech.neychoup.domain.task.model.aggregate.TaskModule;
import tech.neychoup.domain.task.model.aggregate.TaskCompletion;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.service.ITaskService;
import tech.neychoup.types.common.Constants;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/task")
public class TaskController {

    @Resource
    private ITaskService taskService;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Response<List<TaskModule>> generateTask(String skill) {
        try {
            List<TaskModule> taskModules = taskService.generateTask(skill);
            log.info("生成任务：{}", taskModules);
            return Response.<List<TaskModule>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(taskModules)
                    .build();
        } catch (Exception e) {
            log.info("生成任务失败", e);
            return Response.<List<TaskModule>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @PostMapping("/verify")
    public Response<TaskAssignmentResponseDTO> verifyTask(@RequestBody TaskAssignmentRequestDTO submitAssignment) {

        try {
            TaskCompletion taskCompletion = taskService.verifyTaskAssignment(submitAssignment.getTaskId(), submitAssignment.getTextContent(), submitAssignment.getImageUrl());
            log.info("任务完成情况：{}", taskCompletion);
            TaskAssignmentResponseDTO taskAssignmentResponseDTO = TaskAssignmentResponseDTO.builder()
                    .taskId(submitAssignment.getTaskId())
                    .feedback(taskCompletion.getFeedback())
                    .score(taskCompletion.getScore())
                    .completed(taskCompletion.getCompleted())
                    .build();
            return Response.<TaskAssignmentResponseDTO>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(taskAssignmentResponseDTO)
                    .build();
        } catch (Exception e) {
            log.info("查验作业完成情况失败：", e);
            return Response.<TaskAssignmentResponseDTO>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @GetMapping()
    public Response<List<TaskModule>> queryModularTask(Long skillId) {
        try {
            List<TaskModule> taskModules = taskService.queryModularTask(skillId);
            log.info("查询任务：{}", taskModules);
            return Response.<List<TaskModule>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(taskModules)
                    .build();
        } catch (Exception e) {
            log.info("查询任务失败", e);
            return Response.<List<TaskModule>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @GetMapping("/queryTask")
    public Response<Task> queryTaskById(Long taskId) {
        try {
            Task task = taskService.queryTaskById(taskId);
            log.info("查询任务：{}", task);
            return Response.<Task>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(task)
                    .build();
        } catch (Exception e) {
            log.info("查询任务失败", e);
            return Response.<Task>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
