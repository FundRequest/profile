package io.fundrequest.profile.bounty.domain;

import io.fundrequest.profile.common.domain.AuditedEntity;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bounty")
public class Bounty extends AuditedEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private BountyType type;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private BountyStatus status;

    @Builder
    Bounty(String userId, BountyType type) {
        this.userId = userId;
        this.type = type;
        this.status = BountyStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public BountyType getType() {
        return type;
    }

    public BountyStatus getStatus() {
        return status;
    }
}
