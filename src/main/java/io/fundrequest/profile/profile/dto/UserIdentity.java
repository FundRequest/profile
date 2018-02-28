package io.fundrequest.profile.profile.dto;

import io.fundrequest.profile.profile.provider.Provider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIdentity {
    private Provider provider;
    private String username;
    private String userId;

}
