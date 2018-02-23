package io.fundrequest.bounty.profile;

import io.fundrequest.bounty.profile.dto.ProfileDto;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public ModelAndView showProfile(Principal principal) {
        IDToken idToken = ((KeycloakAuthenticationToken) principal).getAccount().getKeycloakSecurityContext().getIdToken();
        ProfileDto profile = ProfileDto.builder()
                .email(idToken.getEmail())
                .name(idToken.getName())
                .build();

        return new ModelAndView("profile")
                .addObject("profile", profile);
    }
}
