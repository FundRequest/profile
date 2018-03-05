package io.fundrequest.profile.linkedin;


import io.fundrequest.profile.linkedin.dto.LinkedInVerificationDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping(value = "/bounties/linkedin")
@Controller
public class LinkedInBountyController {

    private LinkedInService linkedInService;

    public LinkedInBountyController(LinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }

    @PostMapping
    public void verify(Principal principal) {
        linkedInService.verifyLinkedInBounty(principal);
    }

    @GetMapping
    public LinkedInVerificationDto getVerification(Principal principal) {
        return linkedInService.getVerification(principal);
    }
}
