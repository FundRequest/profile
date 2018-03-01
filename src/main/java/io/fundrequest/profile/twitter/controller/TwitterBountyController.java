package io.fundrequest.profile.twitter.controller;

import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.UserProfileProvider;
import io.fundrequest.profile.twitter.controller.vo.ValidatedBountyVO;
import io.fundrequest.profile.twitter.service.TwitterBountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    @ResponseBody
    public ValidatedBountyVO validateBounty(final HttpServletRequest request, final Principal principal) {
        boolean isFollowing = isFollowing(request, principal);
        if (!isFollowing) {
            return new ValidatedBountyVO(false, "You are not following us yet.");
        } else {
            boolean hasFulfilled = hasFullfilledCurrentBounty(request, principal);
            if (hasFulfilled) {
                return new ValidatedBountyVO(true, "Successfully validated your tweet.");
            } else {
                return new ValidatedBountyVO(false, "Tweet was not found in your last 20 tweets.");
            }
        }
    }

    private boolean hasFullfilledCurrentBounty(final HttpServletRequest request, final Principal principal) {
        try {
            final UserProfileProvider twitterProvider = profileService.getUserProfile(request, principal).getTwitter();
            return twitterbountyService.hasFullFilledCurrentBounty(twitterProvider.getUsername(), twitterProvider.getUserId());
        } catch (final Exception ex) {
            return false;
        }
    }

    private boolean isFollowing(final HttpServletRequest request, final Principal principal) {
        try {
            return twitterbountyService.userIsFollowing(profileService.getUserProfile(request, principal).getTwitter().getUsername());
        } catch (final Exception ex) {
            return false;
        }
    }
}
