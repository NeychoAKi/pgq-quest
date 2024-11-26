package tech.neychoup.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
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

    @Test
    public void test() {
        SkillDetailAggregate skillDetailAggregate = skillService.generateTask("公考");
        log.info(skillDetailAggregate.toString());
    }

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void test_getUserInfo() {
        UserInfo userInfo = userRepository.getUserInfo("0x9709206880c0DB1ae9ab9b32d0d8D685217E200b");
        log.info(userInfo.toString());
    }
}
