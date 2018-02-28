package io.fundrequest.profile.ref;

import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class RefSignupEvent {
    private final Principal principal;
    private final String ref;
}
