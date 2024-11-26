package tech.neychoup.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.neychoup.infrastructure.dao.IModuleDao;
import tech.neychoup.infrastructure.dao.po.ModulePO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {

    @Autowired
    private IModuleDao moduleDao;

    @Test
    public void test_queryModuleBySkillId() {
        List<ModulePO> modulePOList = moduleDao.queryModulesBySkillId(1L);
        log.info(modulePOList.toString());
    }
}
