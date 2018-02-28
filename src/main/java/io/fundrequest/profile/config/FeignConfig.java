package io.fundrequest.profile.config;

import io.fundrequest.profile.ProfileApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {ProfileApplication.class})
public class FeignConfig {
}
