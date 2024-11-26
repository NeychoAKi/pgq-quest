package tech.neychoup.infrastructure.adapter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.neychoup.domain.skill.adapter.repository.ISkillRepository;
import tech.neychoup.domain.skill.model.aggregate.SkillDetailAggregate;
import tech.neychoup.infrastructure.dao.IModuleDao;
import tech.neychoup.infrastructure.dao.ISkillDao;
import tech.neychoup.infrastructure.dao.ITaskDao;
import tech.neychoup.infrastructure.dao.po.ModulePO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description Skill 仓储实现
 */
@Repository
public class SkillRepository implements ISkillRepository {

    @Autowired
    private ITaskDao taskDao;

    @Autowired
    private IModuleDao moduleDao;

    // @Autowired
    // private ISkillDao skillDao;

    @Override
    public void saveSkillTask(SkillDetailAggregate skillDetailAggregate) {

    }

    @Override
    public SkillDetailAggregate querySkillDetailBySkillId(Long skillId) {
        List<ModulePO> modulePOList = moduleDao.queryModulesBySkillId(skillId);

        // SkillDetailAggregate skillDetailAggregate = SkillDetailAggregate.builder()
        //           .skill(skillDetailAggregate.getSkill())
        //           .moduleList(skillDetailAggregate.getModuleList())
        //           .build();

        return null;
    }
}
