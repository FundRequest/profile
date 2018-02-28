package io.fundrequest.profile.ref.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "referral")
public class Referral {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "referrer")
    private String referrer;

    @Column(name = "referee")
    private String referee;

    Referral() {
    }

    @Builder
    public Referral(String referrer, String referee) {
        this.referrer = referrer;
        this.referee = referee;
    }

    public Long getId() {
        return id;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getReferee() {
        return referee;
    }
}
