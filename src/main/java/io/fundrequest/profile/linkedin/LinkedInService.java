package io.fundrequest.profile.linkedin;

import io.fundrequest.profile.linkedin.dto.LinkedInVerificationDto;

import java.security.Principal;

public interface LinkedInService {

    LinkedInVerificationDto getVerification(Principal principal);

    void verifyLinkedInBounty(Principal principal);
}
