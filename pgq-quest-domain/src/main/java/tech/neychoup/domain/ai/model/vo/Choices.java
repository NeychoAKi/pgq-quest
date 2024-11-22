package tech.neychoup.domain.ai.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description 选择-模型
 */
@Data
public class Choices {

    private String finish_reason;
    private int index;
    private Message message;

    @Data
    public static class Message {
        private String content;
        private String role;

        @Override
        public String toString() {
            return "Message{" +
                    "content='" + content + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }
    }
}
