package tech.neychoup.trigger.http;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.neychoup.api.dto.TaskGenerationRequestDTO;
import tech.neychoup.api.response.Response;
import tech.neychoup.domain.skill.model.entity.Skill;
import tech.neychoup.domain.skill.service.ISkillService;
import tech.neychoup.types.common.Constants;

import java.util.Collections;
import java.util.List;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-27
 * @description 技能
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private ISkillService skillService;

    @PostMapping("/generate")
    public Response<Skill> generateSkill(@RequestBody @Valid TaskGenerationRequestDTO requestDTO) {
        long startTime = System.currentTimeMillis();
        log.info("Received request to generate task: {}", requestDTO);

        try {
            // 为用户生成技能和其对应任务
            Skill skill = skillService.generateSkill(requestDTO.getWalletAddress(), requestDTO.getSkillTopic());

            long endTime = System.currentTimeMillis();
            log.info("Processed request in {} ms", endTime - startTime);

            return Response.<Skill>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(skill)
                    .build();
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            log.error("Error processing request in {} ms", endTime - startTime);

            // TODO: 统一异常处理
            return Response.<Skill>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    @GetMapping("/getSkillById")
    public Response<Skill> getSkillById(String walletAddress, Long id) {
        log.info("Received request to get skill by id: {}", id);

        try {
            Skill skill = skillService.getSkillById(walletAddress, id);

            return Response.<Skill>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(skill)
                    .build();
        } catch (Exception e) {
            return Response.<Skill>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 根据用户地址获取用户的技能列表
     * @param walletAddress
     * @return
     */
    @GetMapping("/getSkillListByUserAddress")
    public Response<List<Skill>> getSkillListByUserAddress(String walletAddress) {
        log.info("Received request to get skills by address: {}", walletAddress);

        try {
            List<Skill> skillList = skillService.getSkillListByUserAddress(walletAddress);

            if (skillList == null || skillList.isEmpty()) {
                log.warn("No skills found for user with address: {}", walletAddress);
                return Response.<List<Skill>>builder()
                        .code(Constants.ResponseCode.NOT_FOUND.getCode())
                        .info(Constants.ResponseCode.NOT_FOUND.getInfo())
                        .data(Collections.emptyList())
                        .build();
            }

            return Response.<List<Skill>>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(skillList)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while fetching skills for address: {}", walletAddress, e);
            return Response.<List<Skill>>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
