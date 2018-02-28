package io.fundrequest.profile.profile;

import io.fundrequest.profile.profile.dto.UserProfile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface ProfileService {
    UserProfile getUserProfile(HttpServletRequest request, Principal principal);

    void updateEtherAddress(Principal principal, String etherAddress);
}
