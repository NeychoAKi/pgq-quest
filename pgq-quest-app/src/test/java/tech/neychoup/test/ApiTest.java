package tech.neychoup.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.neychoup.domain.task.adapter.port.ITaskPort;
import tech.neychoup.domain.task.model.aggregate.Module;
import tech.neychoup.domain.task.model.entity.Task;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Value("${ai-api.glm-url}")
    private String glmUrl;

    @Value("${ai-api.glm-key}")
    private String glmKey;

    @Test
    public void test() {
        log.info("测试完成");
    }

    public static void parseModules(String json) throws JsonProcessingException {

        // String json = "{\"modules\":[{\"moduleName\":\"Solidity Basics\",\"objective\":\"Master the basic syntax of Solidity and set up a development environment.\",\"tasks\":[{\"taskName\":\"Install and configure development environment\",\"description\":\"Install Node.js, Truffle, and Ganache, and complete the setup of the development environment.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Provide a screenshot showing the completed environment setup.\"},{\"taskName\":\"Understand Solidity data types and variables\",\"description\":\"Learn data types such as boolean, integers, strings, and arrays, as well as variable declarations and assignments.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Write a simple contract demonstrating the use of multiple data types.\"}]}]}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode modulesNode = rootNode.get("modules");
        List<Module> moduleList = mapper.convertValue(modulesNode, new TypeReference<List<Module>>() {
        });
        for (Module module : moduleList) {
            log.info(module.toString());
        }
    }

    @Test
    public void test_generate_task_new() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(glmUrl);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + glmKey);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "glm-4-plus");

        List<Map<String, String>> messages = Stream.of(
                new AbstractMap.SimpleEntry<>("system", "你是一位专业的编程导师。请根据以下要求生成学习任务："),
                new AbstractMap.SimpleEntry<>("user",
                        "将Solidity的学习内容模块化，每个模块包含多个子任务。请确保：\n" +
                                "1. 每个模块有明确的学习目标。\n" +
                                "2. 每个子任务需要提供任务描述、难度系数、代币奖励和经验值奖励。\n" +
                                "3. 经验值奖励应遵循递进式设计，难度越高奖励越多，最高为5级。\n" +
                                "4. 每个子任务需要提供具体的作业要求。\n" +
                                "5. 展示从基础到高级的完整学习路径。\n" +
                                "6. 输出结果以压缩的JSON格式呈现，内容必须保持在一行内，示例如下：\n" +
                                "{\"modules\":[{\"moduleName\":\"Solidity Basics\",\"objective\":\"掌握Solidity的基础语法并设置开发环境。\",\"tasks\":[{\"taskName\":\"安装和配置开发环境\",\"description\":\"安装Node.js、Truffle和Ganache，完成开发环境的配置。\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"提供环境配置完成的截图作为作业。\"},{\"taskName\":\"理解Solidity数据类型和变量\",\"description\":\"学习布尔型、整型、字符串、数组等数据类型，以及变量的声明与赋值。\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"编写一个简单的合约，展示多种数据类型的使用。\"}]},{\"moduleName\":\"高级Solidity特性\",\"objective\":\"探索继承、接口和修饰符等高级特性。\",\"tasks\":[{\"taskName\":\"学习继承和多态\",\"description\":\"理解如何创建基类和派生类，掌握多态的概念。\",\"difficulty\":3,\"tokenReward\":300,\"experienceReward\":250,\"assignment\":\"实现一个展示继承和多态特性的合约。\"},{\"taskName\":\"优化合约性能\",\"description\":\"学习优化合约性能并减少Gas费用的技巧。\",\"difficulty\":4,\"tokenReward\":400,\"experienceReward\":350,\"assignment\":\"优化一个已有的合约，并对比优化前后的Gas使用情况。\"}]}]}\n" +
                                "注意：不要使用任何代码块标记（例如```json```），直接输出纯文本JSON格式，不要有换行或多余的符号。"
                )
        ).map(entry -> {
            Map<String, String> message = new HashMap<>();
            message.put("role", entry.getKey());
            message.put("content", entry.getValue());
            return message;
        }).collect(Collectors.toList());

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0);
        requestBody.put("stream", true); // Enable streaming response

        String paramJson = mapper.writeValueAsString(requestBody);
        post.setEntity(new StringEntity(paramJson, ContentType.APPLICATION_JSON));

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("data: [DONE]")) {
                        break; // End of stream
                    }
                    if (line.startsWith("data:")) {
                        line = line.substring(5).trim(); // Remove "data: " prefix
                        JsonNode jsonNode = mapper.readTree(line);
                        JsonNode delta = jsonNode.at("/choices/0/delta");
                        if (delta.has("content")) {
                            jsonBuilder.append(delta.get("content").asText());
                        }
                    }
                }
                String completeJson = jsonBuilder.toString();
                String cleanJson = completeJson.replaceAll("```json", "").replaceAll("```", "").trim();
                parseModules(cleanJson); // Parse the complete JSON
            }
        } else {
            throw new RuntimeException("Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Resource
    private ITaskPort taskPort;

    @Test
    public void test_taskPort_generate_Tasks() {
        List<Module> moduleList = taskPort.generateLearningTasks("Solidity");
        for (Module module : moduleList) {
            log.info(module.toString());
        }
    }

    @Test
    public void test_taskPort_verify_Tasks() {
        // 模拟任务
        Task task = new Task();
        task.setId(1L);
        task.setSkillId("101");
        task.setTaskName("优化合约性能");
        task.setDescription("学习优化合约性能并减少Gas费用的技巧。");
        task.setDifficulty(4L);
        task.setTokenReward(400L);
        task.setExperienceReward(350L);
        task.setAssignment("优化一个已有的合约，并对比优化前后的Gas使用情况。");

        // 作业答案
        String content = "原合约代码:\n" +
                "                    pragma solidity ^0.8.0;\n" +
                "\n" +
                "                    contract GasInefficient {\n" +
                "                        uint256[] public data;\n" +
                "\n" +
                "                        function addElement(uint256 value) public {\n" +
                "                            data.push(value);\n" +
                "                        }\n" +
                "\n" +
                "                        function calculateSum() public view returns (uint256) {\n" +
                "                            uint256 sum = 0;\n" +
                "                            for (uint256 i = 0; i < data.length; i++) {\n" +
                "                                sum += data[i];\n" +
                "                            }\n" +
                "                            return sum;\n" +
                "                        }\n" +
                "                    }\n" +
                "                    ```\n" +
                "\n" +
                "                    优化后的合约代码:\n" +
                "                    ```\n" +
                "                    pragma solidity ^0.8.0;\n" +
                "\n" +
                "                    contract GasOptimized {\n" +
                "                        uint256[] public data;\n" +
                "                        uint256 public totalSum;\n" +
                "\n" +
                "                        function addElement(uint256 value) public {\n" +
                "                            data.push(value);\n" +
                "                            totalSum += value; // 利用状态变量缓存总和\n" +
                "                        }\n" +
                "\n" +
                "                        function calculateSum() public view returns (uint256) {\n" +
                "                            return totalSum; // 直接返回缓存的总和\n" +
                "                        }\n" +
                "                    }\n" +
                "\n" +
                "                    优化效果:\n" +
                "                    1. 原合约的 `calculateSum` 函数在每次调用时，需要遍历整个数组，消耗 O(n) 的 Gas。\n" +
                "                    2. 优化后，`calculateSum` 直接返回缓存的 `totalSum`，Gas 消耗为 O(1)。\n" +
                "                    3. 测试对比显示，优化后的 `calculateSum` 函数的 Gas 消耗减少了约 80%。";

        Boolean isFinished = null;
        try {
            isFinished = taskPort.verifyTaskFinished(task, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 输出验证结果
        log.info("是否完成任务：{}", isFinished);
    }
}
