package io.fundrequest.profile.bounty.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BountyDTO {

    private long totalRewards;
    private long otherRewards;
    private long referralRewards;
    private long surveyRewards;

}
