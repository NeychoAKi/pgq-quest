package tech.neychoup.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.neychoup.api.dto.TaskAssignmentRequestDTO;
import tech.neychoup.api.dto.TaskAssignmentResponseDTO;
import tech.neychoup.api.response.Response;

import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.domain.task.model.entity.TaskCompletion;
import tech.neychoup.domain.task.model.valobj.TaskCompletionVO;
import tech.neychoup.domain.task.service.ITaskService;
import tech.neychoup.types.common.Constants;

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

    @Autowired
    private ITaskService taskService;

    @PostMapping("/verify")
    public Response<TaskAssignmentResponseDTO> verifyTask(@RequestBody TaskAssignmentRequestDTO submitAssignment) {

        TaskCompletionVO taskCompletionVO = new TaskCompletionVO();
        taskCompletionVO.setWalletAddress(submitAssignment.getWalletAddress());
        taskCompletionVO.setTaskId(submitAssignment.getTaskId());
        taskCompletionVO.setTextContent(submitAssignment.getTextContent());
        taskCompletionVO.setImgUrl(submitAssignment.getImgUrl());

        try {
            TaskCompletion completion = taskService.verifyTaskAssignment(taskCompletionVO);
            log.info("任务完成情况：{}", completion);
            TaskAssignmentResponseDTO taskAssignmentResponseDTO = TaskAssignmentResponseDTO.builder()
                    .taskId(submitAssignment.getTaskId())
                    .feedback(completion.getFeedback())
                    .score(completion.getScore())
                    .completed(completion.getCompleted())
                    .failed(completion.getFailed())
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
}
