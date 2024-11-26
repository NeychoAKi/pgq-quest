package tech.neychoup.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description
 */
@Data
@Builder
@AllArgsConstructor
public class UserSkillPO {

    private String walletAddress;

    private Long skillId;

    private Integer level;

    private Integer experience;

    private Integer progress;

    private Date lastUpdated;
}

