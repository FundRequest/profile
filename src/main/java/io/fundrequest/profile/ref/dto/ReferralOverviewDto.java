package io.fundrequest.profile.ref.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferralOverviewDto {
    private int totalVerified;
    private int totalUnverified;
}
