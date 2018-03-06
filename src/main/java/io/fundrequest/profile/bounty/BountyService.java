package io.fundrequest.profile.bounty;

import io.fundrequest.profile.bounty.dto.BountyDTO;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;

import java.security.Principal;

public interface BountyService {
    void createBounty(CreateBountyCommand createBountyCommand);


    BountyDTO getBounties(Principal principal);
}
