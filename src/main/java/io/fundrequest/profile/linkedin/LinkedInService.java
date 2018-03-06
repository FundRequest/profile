package io.fundrequest.profile.linkedin;

import io.fundrequest.profile.linkedin.dto.LinkedInPostDto;
import io.fundrequest.profile.linkedin.dto.LinkedInVerificationDto;

import java.security.Principal;

public interface LinkedInService {

    LinkedInVerificationDto getVerification(Principal principal);

    void postLinkedInShare(Principal principal, Long postId);

    LinkedInPostDto getRandomPost();
}
