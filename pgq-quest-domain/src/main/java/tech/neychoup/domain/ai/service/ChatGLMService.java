package tech.neychoup.domain.ai.service;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description
 */
public interface ChatGLMService {

    public String generateContent(String prompt) throws IOException;
}
