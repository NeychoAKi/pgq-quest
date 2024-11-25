package tech.neychoup.infrastructure.gateway.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description
 */
@Data
@ToString
public class AIAnswerDTO {

    private String id;
    private String object;
    private int created;
    private String model;
    private String request_id;
    private Usage usage;
    private List<ChoiceDTO> choices;

    @Data
    @ToString
    public static class ChoiceDTO {
        private String finish_reason;
        private int index;
        private MessageDTO message;

        @Data
        @ToString
        public static class MessageDTO {
            private String content;
            private String role;
        }
    }

    @Data
    public static class Usage {
        private int completion_tokens;
        private int prompt_tokens;
        private int total_tokens;
    }
}
