package io.fundrequest.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {ProfileApplication.class, Jsr310JpaConverters.class}
)
public class ProfileApplication {

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }
}
