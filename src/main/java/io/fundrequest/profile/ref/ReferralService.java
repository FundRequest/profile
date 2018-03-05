package io.fundrequest.profile.ref;

import io.fundrequest.profile.ref.dto.ReferralDto;

import java.security.Principal;
import java.util.List;

public interface ReferralService {
    List<ReferralDto> getReferrals(Principal principal);

    Long getTotalVerifiedReferrals(Principal principal);

    void createNewRef(CreateRefCommand command);
}
