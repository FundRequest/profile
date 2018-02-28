package io.fundrequest.profile.bounty;

import io.fundrequest.profile.bounty.event.CreateBountyCommand;

public interface BountyService {
    void createBounty(CreateBountyCommand createBountyCommand);
}
