package io.fundrequest.profile.twitter.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatedBountyVO {
    private boolean validated;
    private String message;
}
