package tech.neychoup.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 技能生成请求DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskGenerationRequestDTO {

    /**
     * 用户钱包地址
     */
    private String walletAddress;

    /**
     * 技能主题
     */
    private String skillTopic;
}
