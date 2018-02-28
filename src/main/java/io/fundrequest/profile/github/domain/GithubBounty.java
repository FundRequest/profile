package io.fundrequest.profile.github.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "github_bounty")
public class GithubBounty {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "github_id")
    private String githubId;

    GithubBounty() {
    }

    @Builder
    GithubBounty(String userId, String githubId) {
        this.userId = userId;
        this.githubId = githubId;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getGithubId() {
        return githubId;
    }
}
