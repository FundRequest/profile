package io.fundrequest;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GithubFeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(
            @Value("${feign.client.github.username}") String githubUsername,
            @Value("${feign.client.github.password}") String githubPassword
    ) {
        return new BasicAuthRequestInterceptor(githubUsername, githubPassword);
    }
}
