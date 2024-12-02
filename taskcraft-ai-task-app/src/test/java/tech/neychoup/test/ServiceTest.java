package tech.neychoup.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tech.neychoup.domain.skill.model.aggregate.UserSkillAggregate;
import tech.neychoup.domain.skill.service.impl.SkillService;
import tech.neychoup.domain.user.adapter.repository.IUserRepository;
import tech.neychoup.domain.user.model.aggregate.UserInfo;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ServiceTest {

    @Autowired
    private SkillService skillService;

    private String userAddress = "0x9709206880c0DB1ae9ab9b32d0d8D685217E200b";

    @Test
    public void test() {
        // UserSkillAggregate userSkill = skillService.generateTask(userAddress, "公考");
        // log.info(userSkill.toString());
    }

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void test_getUserInfo() {
        UserInfo userInfo = userRepository.getUserInfo(userAddress);
        log.info(userInfo.toString());
    }
}
