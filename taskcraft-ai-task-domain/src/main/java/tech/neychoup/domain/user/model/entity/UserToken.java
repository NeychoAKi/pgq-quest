package tech.neychoup.domain.user.model.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 用户代币
 */
@Builder
@Data
public class UserToken {

    /**
     * 代币标识
     */
    private String tokenSymbol;

    /**
     * 所属区块链
     */
    private String blockchain;

    /**
     * 余额
     */
    private BigDecimal balance;
}
