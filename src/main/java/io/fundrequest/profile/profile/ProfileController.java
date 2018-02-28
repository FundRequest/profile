package io.fundrequest.profile.profile;

import io.fundrequest.profile.ref.RefSignupEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ProfileController {

    private ApplicationEventPublisher eventPublisher;
    private ProfileService profileService;

    public ProfileController(ApplicationEventPublisher eventPublisher, ProfileService profileService) {
        this.eventPublisher = eventPublisher;
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ModelAndView showProfile(Principal principal, @RequestParam(value = "ref", required = false) String ref, HttpServletRequest request) {
        if (StringUtils.isNotBlank(ref)) {
            eventPublisher.publishEvent(RefSignupEvent.builder().principal(principal).ref(ref).build());
            return new ModelAndView(new RedirectView("/profile"));
        }
        return new ModelAndView("profile")
                .addObject("profile", profileService.getUserProfile(request, principal));
    }

    @GetMapping("/profile/link/{}/redirect")
    public ModelAndView redirectToHereAfterProfileLink(Principal principal, HttpServletRequest request) {
        return new ModelAndView("profile")
                .addObject("profile", profileService.getUserProfile(request, principal));
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
