package io.fundrequest.rewards.profile.dto;

import io.fundrequest.rewards.profile.provider.Provider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIdentity {
    private Provider provider;
    private String username;
    private String userId;

}
