package io.fundrequest.rewards.profile.dto;

import io.fundrequest.rewards.profile.provider.Provider;
import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class UserLinkedProviderEvent {
    private final Principal principal;
    private final Provider provider;
}
