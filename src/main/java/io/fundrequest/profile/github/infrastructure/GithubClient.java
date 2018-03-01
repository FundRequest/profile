package io.fundrequest.profile.github.infrastructure;

import io.fundrequest.GithubFeignConfiguration;
import io.fundrequest.profile.github.dto.GithubUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(
        name = "github-client",
        url = "https://api.github.com/",
        configuration = GithubFeignConfiguration.class
)
public interface GithubClient {

    @RequestMapping(value = "/users/{username}", method = GET)
    GithubUser getUser(
            @PathVariable("username") String username
    );

}
