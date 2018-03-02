package io.fundrequest.profile.github;

import io.fundrequest.profile.profile.dto.GithubVerificationDto;

import java.security.Principal;

public interface GithubBountyService {

    GithubVerificationDto getVerification(Principal principal);
}
