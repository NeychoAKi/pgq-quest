package tech.neychoup.domain.ai.service.impl;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.neychoup.domain.ai.model.aggregates.AIAnswer;
import tech.neychoup.domain.ai.model.vo.Choices;
import tech.neychoup.domain.ai.service.ChatGLMService;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description
 */
@Slf4j
@Service
public class ChatGLMServiceImpl implements ChatGLMService {

    @Value("${ai-api.glm-url}")
    private String glmUrl;

    @Value("${ai-api.glm-key}")
    private String glmKey;

    @Override
    public String generateContent(String prompt) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(glmUrl);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + glmKey);

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "glm-4-air");
        List<Map<String, String>> messages = Stream.of(
                new AbstractMap.SimpleEntry<>("system", "你是一个专业的编程导师，请根据以下要求生成学习任务"),
                new AbstractMap.SimpleEntry<>("user", "将Solidity的学习内容模块化，每个模块包含子任务。请确保：\n" +
                        "1. 每个模块有明确的学习目标。\n" +
                        "2. 每个子任务提供任务描述、难度系数、代币奖励和经验值奖励。\n" +
                        "3. 经验值设计递进式，难度越高经验奖励越高，5级封顶。\n" +
                        "4. 提供子任务作业要求。\n" +
                        "5. 给出一个从基础到高级的完整学习路径。\n\n" +
                        "示例：\n" +
                        "模块1：Solidity基础入门\n" +
                        "- 任务1：安装与配置开发环境\n" +
                        "  - 描述：安装Node.js、Truffle、Ganache，配置开发环境。\n" +
                        "  - 难度：1\n" +
                        "  - 奖励：代币100，经验50\n" +
                        "  - 作业：截图展示环境配置完成\n" +
                        "- 任务2：理解Solidity数据类型与变量\n" +
                        "  - 描述：学习布尔型、整型、字符串、数组等数据类型。\n" +
                        "  - 难度：1\n" +
                        "  - 奖励：代币100，经验50\n" +
                        "  - 作业：编写一个合约展示多种数据类型的使用。\n\n" +
                        "请根据这一格式继续生成从基础到高级的学习路径。" +
                        "等级分布：\n" +
                        "1级：0-100经验值\n" +
                        "2级：101-500经验值\n" +
                        "3级：501-1000经验值\n" +
                        "4级：1001-2500经验值\n" +
                        "5级：2501+经验值")
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
                answers.append(choice.getMessage().toString());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
