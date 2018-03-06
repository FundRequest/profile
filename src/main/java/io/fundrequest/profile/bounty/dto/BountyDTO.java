package io.fundrequest.profile.bounty.dto;

import io.fundrequest.profile.bounty.domain.Bounty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BountyDTO {

    private long referralCount;
    List<Bounty> bounties;
}
