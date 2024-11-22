package tech.neychoup.test;

import com.alibaba.fastjson2.JSON;
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
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.neychoup.domain.ai.model.aggregates.AIAnswer;
import tech.neychoup.domain.ai.model.aggregates.Module;
import tech.neychoup.domain.ai.model.vo.Choices;

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

    @Test
    public void test_generate_task() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(glmUrl);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + glmKey);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "glm-4-air");

        List<Map<String, String>> messages = Stream.of(
                new AbstractMap.SimpleEntry<>("system", "You are a professional programming mentor. Please generate learning tasks based on the following requirements."),
                new AbstractMap.SimpleEntry<>("user", "Modularize the Solidity learning content, with each module containing subtasks. Please ensure:\n" +
                        "1. Each module has a clear learning objective.\n" +
                        "2. Each subtask provides task description, difficulty level, token reward, and experience reward.\n" +
                        "3. Experience values should follow a progressive design. The higher the difficulty, the greater the reward, with a maximum level of 5.\n" +
                        "4. Provide assignment requirements for each subtask.\n" +
                        "5. Present a complete learning path from beginner to advanced.\n" +
                        "6. Use the following compressed JSON format for tasks:\n" +
                        "{\"modules\":[{\"moduleName\":\"Solidity Basics\",\"objective\":\"Master the basic syntax of Solidity and set up a development environment.\",\"tasks\":[{\"taskName\":\"Install and configure development environment\",\"description\":\"Install Node.js, Truffle, and Ganache, and complete the setup of the development environment.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Provide a screenshot showing the completed environment setup.\"},{\"taskName\":\"Understand Solidity data types and variables\",\"description\":\"Learn data types such as boolean, integers, strings, and arrays, as well as variable declarations and assignments.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Write a simple contract demonstrating the use of multiple data types.\"}]},{\"moduleName\":\"Advanced Solidity Features\",\"objective\":\"Explore advanced features such as inheritance, interfaces, and modifiers.\",\"tasks\":[{\"taskName\":\"Learn about inheritance and polymorphism\",\"description\":\"Understand how to create base and derived contracts, and grasp the concept of polymorphism.\",\"difficulty\":3,\"tokenReward\":300,\"experienceReward\":250,\"assignment\":\"Implement a contract demonstrating inheritance and polymorphism features.\"},{\"taskName\":\"Optimize contract performance\",\"description\":\"Learn techniques to optimize contract performance and reduce gas costs.\",\"difficulty\":4,\"tokenReward\":400,\"experienceReward\":350,\"assignment\":\"Optimize an existing contract and provide a comparison of gas usage before and after optimization.\"}]}]}\n" +
                        "Make sure the response is strictly formatted as per the example above." +
                        "注意，json不需要换行，并且不需要用代码块包裹着，只需要压缩在一起即可")
        ).map(entry -> {
            Map<String, String> message = new HashMap<>();
            message.put("role", entry.getKey());
            message.put("content", entry.getValue());
            return message;
        }).collect(Collectors.toList());

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0);
        requestBody.put("max_tokens", 58888);

        String paramJson = mapper.writeValueAsString(requestBody);
        post.setEntity(new StringEntity(paramJson, ContentType.APPLICATION_JSON));

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            this.parseModules(answers.toString());
            // log.info(answers.toString());
        } else {
            throw new RuntimeException("Err Code is " + response.getStatusLine().getStatusCode());
        }
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
                new AbstractMap.SimpleEntry<>("system", "You are a professional programming mentor. Please generate learning tasks based on the following requirements."),
                new AbstractMap.SimpleEntry<>("user", "Modularize the Solidity learning content, with each module containing subtasks. Please ensure:\n" +
                        "1. Each module has a clear learning objective.\n" +
                        "2. Each subtask provides task description, difficulty level, token reward, and experience reward.\n" +
                        "3. Experience values should follow a progressive design. The higher the difficulty, the greater the reward, with a maximum level of 5.\n" +
                        "4. Provide assignment requirements for each subtask.\n" +
                        "5. Present a complete learning path from beginner to advanced.\n" +
                        "6. Use the following compressed JSON format for tasks:\n" +
                        "{\"modules\":[{\"moduleName\":\"Solidity Basics\",\"objective\":\"Master the basic syntax of Solidity and set up a development environment.\",\"tasks\":[{\"taskName\":\"Install and configure development environment\",\"description\":\"Install Node.js, Truffle, and Ganache, and complete the setup of the development environment.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Provide a screenshot showing the completed environment setup.\"},{\"taskName\":\"Understand Solidity data types and variables\",\"description\":\"Learn data types such as boolean, integers, strings, and arrays, as well as variable declarations and assignments.\",\"difficulty\":1,\"tokenReward\":100,\"experienceReward\":50,\"assignment\":\"Write a simple contract demonstrating the use of multiple data types.\"}]},{\"moduleName\":\"Advanced Solidity Features\",\"objective\":\"Explore advanced features such as inheritance, interfaces, and modifiers.\",\"tasks\":[{\"taskName\":\"Learn about inheritance and polymorphism\",\"description\":\"Understand how to create base and derived contracts, and grasp the concept of polymorphism.\",\"difficulty\":3,\"tokenReward\":300,\"experienceReward\":250,\"assignment\":\"Implement a contract demonstrating inheritance and polymorphism features.\"},{\"taskName\":\"Optimize contract performance\",\"description\":\"Learn techniques to optimize contract performance and reduce gas costs.\",\"difficulty\":4,\"tokenReward\":400,\"experienceReward\":350,\"assignment\":\"Optimize an existing contract and provide a comparison of gas usage before and after optimization.\"}]}]}\n" +
                        "Make sure the response is strictly formatted as per the example above." +
                        "注意，json不需要换行，并且不需要用代码块包裹着，只需要压缩在一起即可")
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
                parseModules(completeJson); // Parse the complete JSON
            }
        } else {
            throw new RuntimeException("Err Code is " + response.getStatusLine().getStatusCode());
        }
    }


}
