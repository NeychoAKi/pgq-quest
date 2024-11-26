package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import tech.neychoup.domain.user.model.entity.UserToken;
import tech.neychoup.infrastructure.dao.po.UserTokenPO;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 用户代币
 */
@Mapper
public interface IUserTokenDao {

    /**
     * 获得用户代币
     * @param walletAddress
     * @return
     */
    UserTokenPO getUserToken(String walletAddress);
}
