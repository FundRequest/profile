package io.fundrequest.profile.stackoverflow;

import io.fundrequest.profile.stackoverflow.dto.StackOverflowVerificationDto;

import java.security.Principal;

public interface StackOverflowBountyService {
    StackOverflowVerificationDto getVerification(Principal principal);
}
