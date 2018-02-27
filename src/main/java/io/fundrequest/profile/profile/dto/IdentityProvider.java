package io.fundrequest.profile.profile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdentityProvider {
    private String username;
}
