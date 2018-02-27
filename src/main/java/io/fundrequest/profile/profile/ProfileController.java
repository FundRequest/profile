package io.fundrequest.profile.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ProfileController {

    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ModelAndView showProfile(Principal principal, HttpServletRequest request) {
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
