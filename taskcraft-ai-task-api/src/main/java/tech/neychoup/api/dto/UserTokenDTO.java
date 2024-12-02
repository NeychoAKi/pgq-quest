package tech.neychoup.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDTO {
    private BigDecimal balance;
    private String blockchain;
    private String tokenSymbol;
}
