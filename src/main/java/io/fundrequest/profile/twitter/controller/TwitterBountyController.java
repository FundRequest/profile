package io.fundrequest.profile.twitter.controller;

import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.twitter.service.TwitterBountyService;
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
    private TwitterBountyService twitterbountyService;

    @GetMapping(value = "/{bountyId}/following")
    @ResponseBody
    public boolean validateBounty(final HttpServletRequest request, final Principal principal, final @PathVariable("bountyId") Long bountyId) {
        boolean isFollowing = isFollowing(request, principal, bountyId);
        boolean hasFulfilled = hasFulFilled(request, principal, bountyId);
        return isFollowing && hasFulfilled;
    }

    private boolean hasFulFilled(final HttpServletRequest request, final Principal principal, final Long bountyId) {
        try {
            return twitterbountyService.hasFullFilled(profileService.getUserProfile(request, principal).getTwitter().getUsername(), bountyId);
        } catch (final Exception ex) {
            return false;
        }
    }

    private boolean isFollowing(final HttpServletRequest request, final Principal principal, final Long bountyId) {
        try {
            return twitterbountyService.userIsFollowing(profileService.getUserProfile(request, principal).getTwitter().getUsername());
        } catch (final Exception ex) {
            return false;
        }
    }
}
