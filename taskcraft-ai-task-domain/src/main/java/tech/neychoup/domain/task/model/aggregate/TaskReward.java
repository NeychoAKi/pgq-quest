package tech.neychoup.domain.task.model.aggregate;

import lombok.Builder;
import lombok.Data;
import tech.neychoup.domain.skill.model.entity.Skill;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-29
 * @description
 */
@Data
@Builder
public class TaskReward {

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 技能升级奖励
     */
    private Skill skill;

    /**
     * 代币奖励
     */
    private String token;
}
