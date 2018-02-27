package io.fundrequest.profile.profile.provider;

import io.fundrequest.profile.profile.dto.UserProfile;

import java.security.Principal;

public interface ProviderProfileService {
    Provider getProvider();

    void userLinked(UserProfile userProfile);

    void claimBounty(Principal principal);
}
