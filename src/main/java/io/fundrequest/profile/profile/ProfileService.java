package io.fundrequest.profile.profile;

import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.provider.Provider;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface ProfileService {
    void userProviderIdentityLinked(Principal principal, Provider provider);

    UserProfile getUserProfile(HttpServletRequest request, Principal principal);

    void updateEtherAddress(Principal principal, String etherAddress);

    void updateTelegramName(Principal principal, String telegramName);

    void updateHeadline(Principal principal, String headline);
}
