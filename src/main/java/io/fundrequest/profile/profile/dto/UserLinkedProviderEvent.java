package io.fundrequest.profile.profile.dto;

import io.fundrequest.profile.profile.provider.Provider;
import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class UserLinkedProviderEvent {
    private final Principal principal;
    private final Provider provider;
}
