package tech.neychoup.domain.skill.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-26
 * @description 技能实体对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Skill {

    /**
     * 技能ID
     */
    private Long id;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
    private String description;

    /**
     * 技能下的模块列表
     */
    private List<Module> moduleList;
}
