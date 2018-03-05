package io.fundrequest.profile.profile;

import io.fundrequest.profile.github.GithubBountyService;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.dto.UserProfileProvider;
import io.fundrequest.profile.profile.provider.Provider;
import io.fundrequest.profile.ref.RefSignupEvent;
import io.fundrequest.profile.stackoverflow.StackOverflowBountyService;
import io.fundrequest.profile.twitter.model.TwitterBounty;
import io.fundrequest.profile.twitter.model.TwitterPost;
import io.fundrequest.profile.twitter.service.TwitterBountyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    private ApplicationEventPublisher eventPublisher;
    private ProfileService profileService;
    private TwitterBountyService twitterBountyService;
    private GithubBountyService githubBountyService;
    private StackOverflowBountyService stackOverflowBountyService;


    public ProfileController(ApplicationEventPublisher eventPublisher, ProfileService profileService, TwitterBountyService twitterBountyService, GithubBountyService githubBountyService, StackOverflowBountyService stackOverflowBountyService) {
        this.eventPublisher = eventPublisher;
        this.profileService = profileService;
        this.twitterBountyService = twitterBountyService;
        this.githubBountyService = githubBountyService;
        this.stackOverflowBountyService = stackOverflowBountyService;
    }

    @GetMapping("/profile")
    public ModelAndView showProfile(Principal principal, @RequestParam(value = "ref", required = false) String ref, HttpServletRequest request) {
        if (StringUtils.isNotBlank(ref)) {
            eventPublisher.publishEvent(RefSignupEvent.builder().principal(principal).ref(ref).build());
            return redirectToProfile();
        }
        final ModelAndView mav = new ModelAndView("profile");
        final UserProfile userProfile = profileService.getUserProfile(request, principal);
        mav.addObject("profile", userProfile);
        enrichTwitter(mav, userProfile);
        mav.addObject("githubVerification", githubBountyService.getVerification(principal));
        mav.addObject("stackOverflowVerification", stackOverflowBountyService.getVerification(principal));
        mav.addObject("refLink", getRefLink(request, principal));
        return mav;
    }

    private static String getRefLink(HttpServletRequest req, Principal principal) {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath).append("?ref=").append(principal.getName());
        return url.toString();
    }

    private void enrichTwitter(ModelAndView mav, UserProfile userProfile) {
        if (userProfile.getTwitter() != null) {
            final Optional<TwitterBounty> activeBounty = twitterBountyService.getActiveBounty();
            if (activeBounty.isPresent()) {
                mav.addObject("activeBounty", activeBounty);
                if (claimedTwitterBounty(userProfile.getTwitter(), activeBounty.get())) {
                    mav.addObject("claimedTwitterBounty", true);
                } else {
                    mav.addObject("claimedTwitterBounty", false);
                    final List<TwitterPost> posts = new ArrayList<>(twitterBountyService.getTwitterPosts());
                    Collections.shuffle(twitterBountyService.getTwitterPosts());
                    mav.addObject("twitterPost", posts.get(0));
                }
            }
        }
    }

    private boolean claimedTwitterBounty(final UserProfileProvider twitter, final TwitterBounty twitterBounty) {
        return twitterBountyService.claimedBountyAlready(twitter.getUserId(), twitterBounty);
    }

    @GetMapping("/profile/link/{provider}/redirect")
    public ModelAndView redirectToHereAfterProfileLink(Principal principal, @PathVariable("provider") String provider, HttpServletRequest request) {
        profileService.userProviderIdentityLinked(principal, Provider.fromString(provider));
        return redirectToProfile();
    }

    private ModelAndView redirectToProfile() {
        return new ModelAndView(new RedirectView("/profile"));
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
