package io.fundrequest.rewards.profile;

import io.fundrequest.rewards.profile.dto.UserProfile;
import io.fundrequest.rewards.profile.provider.Provider;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface ProfileService {
    void userProviderIdentityLinked(Principal principal, Provider provider);

    UserProfile getUserProfile(HttpServletRequest request, Principal principal);

    void updateEtherAddress(Principal principal, String etherAddress);
}
