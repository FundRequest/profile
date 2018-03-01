package io.fundrequest.profile.twitter.controller;

import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.UserProfileProvider;
import io.fundrequest.profile.twitter.controller.vo.ValidatedBountyVO;
import io.fundrequest.profile.twitter.service.TwitterBountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping(value = "/bounties/twitter")
@Controller
public class TwitterBountyController {


    @Autowired
    private ProfileService profileService;
    @Autowired
    private TwitterBountyService twitterbountyService;

    @PostMapping("/verify")
    public String validateBounty(final HttpServletRequest request, final Principal principal, final RedirectAttributes redirectAttributes) {
        boolean isFollowing = isFollowing(request, principal);
        if (!isFollowing) {
            redirectAttributes.addFlashAttribute("validatedTwitterBountyResult", new ValidatedBountyVO(false, "You are not following us yet. Please follow us before claiming."));
        } else {
            boolean hasFulfilled = hasFullfilledCurrentBounty(request, principal);
            if (hasFulfilled) {
                redirectAttributes.addFlashAttribute("validatedTwitterBountyResult", new ValidatedBountyVO(true, "Successfully validated your tweet."));
            } else {

                redirectAttributes.addFlashAttribute("validatedTwitterBountyResult", new ValidatedBountyVO(false, "Tweet was not found in your last 20 tweets."));
            }
        }
        return "redirect:/profile#twitter";
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
