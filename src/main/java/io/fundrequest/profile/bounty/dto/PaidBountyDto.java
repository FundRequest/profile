package io.fundrequest.profile.bounty.dto;

import io.fundrequest.profile.bounty.domain.BountyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaidBountyDto {
    private BountyType type;
    private int amount;
    private String transactionHash;
}
