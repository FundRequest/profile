package io.fundrequest.profile.linkedin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@RequestMapping(value = "/bounties/linkedin")
@Controller
public class LinkedInBountyController {

    private LinkedInService linkedInService;

    public LinkedInBountyController(LinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }

    @GetMapping("")
    @ResponseBody
    public String getLInkedIn(Principal principal) {
        linkedInService.verifyLinkedInBounty(principal);
        return "";
    }
}
