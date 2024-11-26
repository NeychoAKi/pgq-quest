package tech.neychoup.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.neychoup.infrastructure.gateway.TaskAssistant;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 大模型配置
 */
@Configuration()
public class LLMConfig {

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.open-ai.chat-model.model-name}")
    private String modelName;

    @Value("${langchain4j.open-ai.chat-model.base-url}")
    private String baseUrl;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public TaskAssistant taskAssistant() {
        return AiServices.builder(TaskAssistant.class)
                .chatLanguageModel(chatLanguageModel())
                .build();
    }
}