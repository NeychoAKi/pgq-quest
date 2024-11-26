package tech.neychoup.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.neychoup.infrastructure.dao.po.ModulePO;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-24
 * @description
 */
@Mapper
public interface IModuleDao {

    void saveModuleList(List<ModulePO> modulePOList);

    List<ModulePO> queryModulesBySkillId(Long skillId);

    void saveModule(ModulePO modulePO);
}
