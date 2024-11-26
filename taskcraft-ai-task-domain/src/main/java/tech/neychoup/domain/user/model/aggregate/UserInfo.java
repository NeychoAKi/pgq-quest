package tech.neychoup.domain.user.model.aggregate;

import lombok.Builder;
import lombok.Data;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.user.model.entity.UserSkill;
import tech.neychoup.domain.user.model.entity.UserToken;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 用户钱包
 */
@Builder
@Data
public class UserInfo {

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 持有代币
     */
    private UserToken userToken;

    /**
     * 技能列表
     */
    private List<UserSkill> userSkills;
}
