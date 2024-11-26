package tech.neychoup.infrastructure.adapter.repository;

import org.springframework.stereotype.Repository;
import tech.neychoup.domain.user.adapter.repository.IUserRepository;
import tech.neychoup.domain.user.model.aggregate.UserInfo;
import tech.neychoup.domain.user.model.entity.UserSkill;
import tech.neychoup.domain.user.model.entity.UserToken;
import tech.neychoup.infrastructure.dao.ISkillDao;
import tech.neychoup.infrastructure.dao.IUserSkillDao;
import tech.neychoup.infrastructure.dao.IUserTokenDao;
import tech.neychoup.infrastructure.dao.po.SkillPO;
import tech.neychoup.infrastructure.dao.po.UserSkillPO;
import tech.neychoup.infrastructure.dao.po.UserTokenPO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description
 */
@Repository
public class UserRepository implements IUserRepository {

    private final IUserSkillDao userSkillDao;
    private final ISkillDao skillDao;
    private final IUserTokenDao userTokenDao;

    public UserRepository(IUserSkillDao userSkillDao, ISkillDao skillDao, IUserTokenDao userTokenDao) {
        this.userSkillDao = userSkillDao;
        this.skillDao = skillDao;
        this.userTokenDao = userTokenDao;
    }

    @Override
    public UserInfo getUserInfo(String walletAddress) {
        // 1. 获取用户技能列表
        List<UserSkillPO> userSkillPOList = userSkillDao.getUserSkill(walletAddress);

        List<UserSkill> userSkills = new ArrayList<>();

        userSkillPOList.forEach(userSkillPO -> {
            SkillPO skillPO = skillDao.getSkillById(userSkillPO.getSkillId());
            UserSkill userSkill = UserSkill.builder()
                    .skillId(skillPO.getId())
                    .name(skillPO.getName())
                    .description(skillPO.getDescription())
                    .level(userSkillPO.getLevel())
                    .experience(userSkillPO.getExperience())
                    .progress(userSkillPO.getProgress())
                    .build();

            userSkills.add(userSkill);
        });

        // 2. 获取用户代币信息
        UserTokenPO userTokenPO = userTokenDao.getUserToken(walletAddress);

        UserToken userToken = UserToken.builder()
                .tokenSymbol(userTokenPO.getTokenSymbol())
                .blockchain(userTokenPO.getBlockchain())
                .balance(userTokenPO.getBalance())
                .build();

        // 3. 构建用户信息
        UserInfo userInfo = UserInfo.builder()
               .walletAddress(walletAddress)
               .userToken(userToken)
               .userSkills(userSkills)
               .build();

        return userInfo;
    }
}
