package io.fundrequest.profile.bounty.event;

import io.fundrequest.profile.bounty.domain.BountyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBountyCommand {
    private final String userId;
    private final BountyType type;
}
