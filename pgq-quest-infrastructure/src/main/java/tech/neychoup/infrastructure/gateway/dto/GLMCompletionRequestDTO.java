package tech.neychoup.infrastructure.gateway.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description GLM请求DTO
 */
@Data
public class GLMCompletionRequestDTO {

    private String model;
    private List<Map<String, String>> messages;
    private double temperatures;
    private boolean stream;
}
