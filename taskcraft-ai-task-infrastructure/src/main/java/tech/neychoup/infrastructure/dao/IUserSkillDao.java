package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import tech.neychoup.domain.user.model.entity.UserSkill;
import tech.neychoup.infrastructure.dao.po.UserSkillPO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 用户技能
 */
@Mapper
public interface IUserSkillDao {

    /**
     * 获得用户技能
     * @param walletAddress
     * @return
     */
    List<UserSkillPO> getUserSkill(String walletAddress);

    void saveUserSkill(UserSkillPO userSkillPO);
}
