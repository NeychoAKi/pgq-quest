package tech.neychoup.domain.ai.model.aggregates;

import lombok.Data;
import tech.neychoup.domain.ai.model.vo.Choices;

import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-22
 * @description AI回答
 */
@Data
public class AIAnswer {

    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choices> choices;
}
