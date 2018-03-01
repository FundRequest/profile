package io.fundrequest.rewards.profile.provider;

import io.fundrequest.rewards.profile.dto.UserProfile;

import java.security.Principal;

public interface ProviderProfileService {
    Provider getProvider();

    void userLinked(UserProfile userProfile);

    void claimBounty(Principal principal);
}
