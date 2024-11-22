package tech.neychoup.infrastructure.gateway;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tech.neychoup.domain.ai.model.vo.Choices;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description API网关 与外部ChatGPt接口
 */
public class ChatGPTGateway {

    private final RestTemplate restTemplate;
    private final String apiKey = "";
    private final String apiUrl = "";

    public ChatGPTGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callChatGPT(String prompt) {
        // 构造请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 150);
        requestBody.put("temperature", 0.7);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
        Map responseBody = response.getBody();

        Choices choices = (Choices) responseBody.get("choices");
        return null;
    }
}
