package io.fundrequest.profile.profile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfile {
    private String id;
    private String name;
    private String email;
    private String etherAddress;
    private String telegramName;
    private String picture;
    private boolean verifiedDeveloper;
    private UserProfileProvider github;
    private UserProfileProvider linkedin;
    private UserProfileProvider twitter;
    private UserProfileProvider stackoverflow;
    private UserProfileProvider google;
}
