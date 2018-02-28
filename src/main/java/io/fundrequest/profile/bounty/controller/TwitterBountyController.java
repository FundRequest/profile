package io.fundrequest.profile.bounty.controller;

import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping(value = "/bounties/twitter")
@Controller
public class TwitterBountyController {


    @Autowired
    private ProfileService profileService;
    @Autowired
    private TwitterService twitterService;

    @GetMapping(value = "/{bountyId}/following")
    @ResponseBody
    public boolean validateBounty(final HttpServletRequest request, final Principal principal, final @PathVariable("bountyId") String bountyId) {


//
//        try {
//
//        }
//        twitterService.userIsFollowing(profileService.getUserProfile(request, principal).getTwitter())
        return "OK";
    }
}
