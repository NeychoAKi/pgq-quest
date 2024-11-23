package tech.neychoup.infrastructure.gateway;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tech.neychoup.infrastructure.gateway.dto.AIAnswerDTO;
import tech.neychoup.infrastructure.gateway.dto.GLMCompletionRequestDTO;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description
 */
public interface IChatGLMApiService {

    @POST("paas/v4/chat/completions")
    Call<AIAnswerDTO> generateCompletion(
            @Body GLMCompletionRequestDTO requestDTO
    );
}
