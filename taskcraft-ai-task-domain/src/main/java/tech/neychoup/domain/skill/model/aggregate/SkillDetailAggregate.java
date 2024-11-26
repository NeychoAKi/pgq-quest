package tech.neychoup.domain.skill.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.neychoup.domain.skill.model.entity.Module;
import tech.neychoup.domain.skill.model.entity.Skill;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 技能详情聚合类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillDetailAggregate {

    /**
     *
     */
    private Skill skill;

    /**
     * 任务模块列表（包含任务）
     */
    private List<Module> moduleList;

}
