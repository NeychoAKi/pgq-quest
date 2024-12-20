package tech.neychoup.infrastructure.adapter.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.aggregate.TaskModule;
import tech.neychoup.domain.task.model.aggregate.TaskCompletion;
import tech.neychoup.domain.task.model.entity.Task;
import tech.neychoup.infrastructure.gateway.IChatGLMApiService;
import tech.neychoup.infrastructure.gateway.dto.AIAnswerDTO;
import tech.neychoup.infrastructure.gateway.dto.GLMCompletionRequestDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description AI任务接口
 */
@Service
public class TaskPort implements ITaskPort {

    @Value("${ai-api.model}")
    private String apiModel;
    @Resource
    private IChatGLMApiService chatGLMApiService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<TaskModule> generateLearningTasks(String skill) {
        try {
            // 1. 构建请求数据
            GLMCompletionRequestDTO requestDTO = new GLMCompletionRequestDTO();
            requestDTO.setModel(apiModel);
            requestDTO.setMessages(buildRequestPayload(skill));
            requestDTO.setTemperatures(0);
            requestDTO.setStream(false);

            Call<AIAnswerDTO> call = chatGLMApiService.generateCompletion(
                    requestDTO
            );

            // 2. 执行AI生成任务
            AIAnswerDTO resp = call.execute().body();
            if (resp == null || resp.getChoices().isEmpty()) {
                throw new RuntimeException("Empty response from ChatGLM");
            }

            // 3. 拼接模型返回内容
            StringBuilder answers = new StringBuilder();
            for (AIAnswerDTO.ChoiceDTO choice : resp.getChoices()) {
                answers.append(choice.getMessage().getContent());
            }

            // 4. 返回构建好的Module
            return parseModules(answers.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to call ChatGLM", e);
        }
    }

    @Override
    public TaskCompletion verifyTaskFinished(Task task, String content) throws IOException {
        GLMCompletionRequestDTO requestDTO = new GLMCompletionRequestDTO();
        requestDTO.setModel(apiModel);
        requestDTO.setMessages(buildVerificationPayload(task, content));
        requestDTO.setTemperatures(0);
        requestDTO.setStream(false);

        Call<AIAnswerDTO> call = chatGLMApiService.generateCompletion(requestDTO);
        AIAnswerDTO resp = call.execute().body();

        if (resp == null || resp.getChoices().isEmpty()) {
            throw new RuntimeException("Empty response from ChatGLM for verification");
        }

        // 拼接模型返回内容
        StringBuilder answers = new StringBuilder();
        for (AIAnswerDTO.ChoiceDTO choice : resp.getChoices()) {
            answers.append(choice.getMessage().getContent());
        }

        // 解析返回的 JSON 格式审核结果
        JsonNode resultNode = objectMapper.readTree(getCleanJson(answers.toString()));
        double score = resultNode.get("score").asDouble();
        String feedback = resultNode.get("feedback").asText();

        TaskCompletion taskCompletion = new TaskCompletion();
        taskCompletion.setScore(BigDecimal.valueOf(score));
        taskCompletion.setCompleted(score >= 90);
        taskCompletion.setFeedback(feedback);

        return taskCompletion;
    }

    private List<Map<String, String>> buildRequestPayload(String skill) {
        List<Map<String, String>> messages = Stream.of(
                new AbstractMap.SimpleEntry<>("system", "你是一位专业的" + skill + "导师。请根据以下要求生成学习任务："),
                new AbstractMap.SimpleEntry<>("user",
                        "将" + skill + "的学习内容模块化，每个模块包含多个子任务。请确保：\n" +
                                "1. 每个模块有明确的学习目标。\n" +
                                "2. 每个子任务需要提供任务描述、难度系数、代币奖励和经验值奖励。\n" +
                                "3. 经验值奖励应遵循递进式设计，难度越高奖励越多，最高为5级。\n" +
                                "4. 每个子任务需要提供具体的作业要求。\n" +
                                "5. 展示从基础到高级的完整学习路径。\n" +
                                "6. 输出结果以压缩的JSON格式呈现，示例如下：\n" +
                                "{\"modules\":[{\"moduleName\":\"Solidity Basics\",\"objective\":\"掌握Solidity的基础语法并设置开发环境。\",\"tasks\":[{\"taskName\":\"安装和配置开发环境\",\"description\":\"安装Node.js、Truffle和Ganache，完成开发环境的配置。\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"提供环境配置完成的截图作为作业。\"},{\"taskName\":\"理解Solidity数据类型和变量\",\"description\":\"学习布尔型、整型、字符串、数组等数据类型，以及变量的声明与赋值。\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"编写一个简单的合约，展示多种数据类型的使用。\"}]},{\"moduleName\":\"高级Solidity特性\",\"objective\":\"探索继承、接口和修饰符等高级特性。\",\"tasks\":[{\"taskName\":\"学习继承和多态\",\"description\":\"理解如何创建基类和派生类，掌握多态的概念。\",\"difficulty\":3,\"tokenReward\":300,\"experienceReward\":250,\"assignment\":\"实现一个展示继承和多态特性的合约。\"},{\"taskName\":\"优化合约性能\",\"description\":\"学习优化合约性能并减少Gas费用的技巧。\",\"difficulty\":4,\"tokenReward\":400,\"experienceReward\":350,\"assignment\":\"优化一个已有的合约，并对比优化前后的Gas使用情况。\"}]}]}\n" +
                                "注意：JSON格式不需要换行或使用代码块修饰，输出内容必须严格按照以上示例格式。确保生成内容清晰准确。"
                )
        ).map(entry -> {
            Map<String, String> message = new HashMap<>();
            message.put("role", entry.getKey());
            message.put("content", entry.getValue());
            return message;
        }).collect(Collectors.toList());

        return messages;
    }

    /**
     * 构建AI接口请求内容（审查任务）
     * @param task 任务
     * @param content 用户提交作业内容
     * @return
     */
    private List<Map<String, String>> buildVerificationPayload(Task task, String content) {
        List<Map<String, String>> messages = Stream.of(
                new AbstractMap.SimpleEntry<>("system", "你是一位专业的任务审核员。请根据以下要求审核用户提交的作业："),
                new AbstractMap.SimpleEntry<>("user",
                        "任务描述：" + task.getDescription() + "\n" +
                                "作业要求：" + task.getAssignment() + "\n" +
                                "用户提交内容：" + content + "\n" +
                                "请根据以下规则进行审核：\n" +
                                "1. 审核用户的提交内容是否符合作业要求。\n" +
                                "2. 提供完成度评分，满分100分。\n" +
                                "3. 如果未完成或部分完成，请具体说明用户未满足的部分。\n" +
                                "输出格式如下：\n" +
                                "{\"score\":分数,\"feedback\":\"具体反馈内容\"}\n" +
                                "确保输出格式正确，且内容清晰准确。"
                )
        ).map(entry -> {
            Map<String, String> message = new HashMap<>();
            message.put("role", entry.getKey());
            message.put("content", entry.getValue());
            return message;
        }).collect(Collectors.toList());

        return messages;
    }

    private List<TaskModule> parseModules(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String cleanJson = getCleanJson(json);

        JsonNode rootNode = mapper.readTree(cleanJson);
        JsonNode modulesNode = rootNode.get("modules");
        List<TaskModule> taskModuleList = mapper.convertValue(modulesNode, new TypeReference<List<TaskModule>>() {
        });
        return taskModuleList;
    }

    private String getCleanJson(String json) {
        return json.replaceAll("```json", "").replaceAll("```", "").trim();
    }
}
