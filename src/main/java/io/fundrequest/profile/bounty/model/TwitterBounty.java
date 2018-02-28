package io.fundrequest.profile.bounty.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@DiscriminatorValue("twitter")
@Data
public class TwitterBounty extends Bounty {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "twitter_type")
    private TwitterBountyType type;
    @Column(name = "twittter_follow_account")
    private String followAccount;
    @Column(name = "twittter_required_posts")
    private int requiredPosts = 1;
}
