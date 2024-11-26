package tech.neychoup.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.neychoup.api.dto.TaskAssignmentRequestDTO;
import tech.neychoup.api.dto.TaskAssignmentResponseDTO;
import tech.neychoup.api.response.Response;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.domain.task.model.aggregate.AssignmentCompletion;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.types.common.Constants;

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

    // @Autowired
    // private ITaskService taskService;
    //
    // /**
    //  * 应用AI生成技能任务
    //  * @param skillName 技能名称
    //  * @return 任务
    //  */
    // @PostMapping(value = "/generate")
    // public Response<SkillDetailAggregate> generateTask(@RequestParam String skillName) {
    //     try {
    //         SkillDetailAggregate taskModules = taskService.generateTask(skillName);
    //         log.info("生成任务：{}", taskModules);
    //         return Response.<SkillDetailAggregate>builder()
    //                 .code(Constants.ResponseCode.SUCCESS.getCode())
    //                 .info(Constants.ResponseCode.SUCCESS.getInfo())
    //                 .data(taskModules)
    //                 .build();
    //     } catch (Exception e) {
    //         log.info("生成任务失败", e);
    //         return Response.<SkillDetailAggregate>builder()
    //                 .code(Constants.ResponseCode.UN_ERROR.getCode())
    //                 .info(Constants.ResponseCode.UN_ERROR.getInfo())
    //                 .build();
    //     }
    // }
    //
    // @PostMapping("/verify")
    // public Response<TaskAssignmentResponseDTO> verifyTask(@RequestBody TaskAssignmentRequestDTO submitAssignment) {
    //
    //     try {
    //         AssignmentCompletion assignmentCompletion = taskService.verifyTaskAssignment(submitAssignment.getTaskId(), submitAssignment.getTextContent(), submitAssignment.getImageUrl());
    //         log.info("任务完成情况：{}", assignmentCompletion);
    //         TaskAssignmentResponseDTO taskAssignmentResponseDTO = TaskAssignmentResponseDTO.builder()
    //                 .taskId(submitAssignment.getTaskId())
    //                 .feedback(assignmentCompletion.getFeedback())
    //                 .score(assignmentCompletion.getScore())
    //                 .completed(assignmentCompletion.getCompleted())
    //                 .build();
    //         return Response.<TaskAssignmentResponseDTO>builder()
    //                 .code(Constants.ResponseCode.SUCCESS.getCode())
    //                 .info(Constants.ResponseCode.SUCCESS.getInfo())
    //                 .data(taskAssignmentResponseDTO)
    //                 .build();
    //     } catch (Exception e) {
    //         log.info("查验作业完成情况失败：", e);
    //         return Response.<TaskAssignmentResponseDTO>builder()
    //                 .code(Constants.ResponseCode.UN_ERROR.getCode())
    //                 .info(Constants.ResponseCode.UN_ERROR.getInfo())
    //                 .build();
    //     }
    // }
    //
    // @GetMapping()
    // public Response<List<TaskModule>> queryModularTask(Long skillId) {
    //     try {
    //         List<TaskModule> taskModules = taskService.queryModularTask(skillId);
    //         log.info("查询任务：{}", taskModules);
    //         return Response.<List<TaskModule>>builder()
    //                 .code(Constants.ResponseCode.SUCCESS.getCode())
    //                 .info(Constants.ResponseCode.SUCCESS.getInfo())
    //                 .data(taskModules)
    //                 .build();
    //     } catch (Exception e) {
    //         log.info("查询任务失败", e);
    //         return Response.<List<TaskModule>>builder()
    //                 .code(Constants.ResponseCode.UN_ERROR.getCode())
    //                 .info(Constants.ResponseCode.UN_ERROR.getInfo())
    //                 .build();
    //     }
    // }
    //
    // @GetMapping("/queryTask")
    // public Response<Task> queryTaskById(Long taskId) {
    //     try {
    //         Task task = taskService.queryTaskById(taskId);
    //         log.info("查询任务：{}", task);
    //         return Response.<Task>builder()
    //                 .code(Constants.ResponseCode.SUCCESS.getCode())
    //                 .info(Constants.ResponseCode.SUCCESS.getInfo())
    //                 .data(task)
    //                 .build();
    //     } catch (Exception e) {
    //         log.info("查询任务失败", e);
    //         return Response.<Task>builder()
    //                 .code(Constants.ResponseCode.UN_ERROR.getCode())
    //                 .info(Constants.ResponseCode.UN_ERROR.getInfo())
    //                 .build();
    //     }
    // }
}
