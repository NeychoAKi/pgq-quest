package tech.neychoup.config;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import tech.neychoup.infrastructure.gateway.IChatGLMApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Neycho
 * @version 1.0
 * @date 2024-11-23
 * @description Retrofit Configuration for ChatGLM API.
 */
@Configuration
public class Retrofit2Config {
    @Value("${ai-api.glm-url}")
    private String glmUrl;

    @Value("${ai-api.glm-key}")
    private String glmKey;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(glmUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
                .readTimeout(30, TimeUnit.SECONDS) // Read timeout
                .addInterceptor(defaultHeadersInterceptor()) // Add headers
                .addInterceptor(loggingInterceptor()) // Add logging
                .build();
    }

    private Interceptor defaultHeadersInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .addHeader("Content-Type", "application/json") // JSON content
                        .addHeader("Authorization", "Bearer " + glmKey) // Authorization header
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Logs request and response body
        return loggingInterceptor;
    }

    @Bean
    public IChatGLMApiService chatGLMApiService(Retrofit retrofit) {
        return retrofit.create(IChatGLMApiService.class);
    }
}
