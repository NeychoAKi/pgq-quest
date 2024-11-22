package tech.neychoup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description ChatGLM配置类
 */
@Configuration
public class ChatGLMConfig {

    @Value("")
    private String CHATGLM_API_BASE_URL;

    private static final int TIMEOUT = 5000;

    /**
     * 配置 RestTemplate，与 ChatGLM 进行 Http 通信
     * @return
     */
    @Bean
    public RestTemplate chatGLMRestTemplate() {
        return new RestTemplate();
    }

}
