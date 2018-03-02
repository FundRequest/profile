package io.fundrequest.profile.stackoverflow.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StackOverflowVerificationDto {
    private final LocalDateTime createdAt;
    private final boolean approved;
}
