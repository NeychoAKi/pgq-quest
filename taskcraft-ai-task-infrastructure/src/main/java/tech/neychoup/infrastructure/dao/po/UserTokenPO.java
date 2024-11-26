package tech.neychoup.infrastructure.dao.po;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description
 */
@Data
@Builder
public class UserTokenPO {

    private String walletAddress;

    private String tokenSymbol;

    private BigDecimal balance;

    private String blockchain;

    private Timestamp lastUpdated;
}
