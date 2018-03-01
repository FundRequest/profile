package io.fundrequest.profile.github.infrastructure;

import io.fundrequest.profile.github.domain.GithubBounty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubBountyRepository extends JpaRepository<GithubBounty, Long> {
    boolean existsByUserId(String userId);
}
