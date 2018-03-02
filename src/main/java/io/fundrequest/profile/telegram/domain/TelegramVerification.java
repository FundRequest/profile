package io.fundrequest.profile.telegram.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "telegram_verifications")
@Data
public class TelegramVerification {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "telegram_name")
    private String telegramName;
    private String secret;
    private boolean verified;
    @Column(name = "last_action")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAction;
}
