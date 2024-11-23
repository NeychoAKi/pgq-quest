package tech.neychoup.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.neychoup.api.response.Response;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.aggregate.Module;
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
    private ITaskPort taskPort;

    @Resource
    private ITaskService taskService;

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public Response<List<Module>> generateTask(String skill) {
        try {
            List<Module> modules = taskPort.generateLearningTasks(skill);
            log.info("生成任务：{}", modules);
            taskService.saveGeneratedTask(modules);
            return Response.<List<Module>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(modules)
                    .build();
        } catch (Exception e) {
            log.info("生成任务失败", e);
            return Response.<List<Module>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    // @RequestMapping("/verify")
    // public Response<> verifyTask() {
    //
    // }
}
