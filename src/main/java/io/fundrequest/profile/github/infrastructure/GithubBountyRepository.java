package io.fundrequest.profile.github.infrastructure;

import io.fundrequest.profile.github.domain.GithubBounty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GithubBountyRepository extends JpaRepository<GithubBounty, Long> {
    boolean existsByUserId(String userId);

    Optional<GithubBounty> findByUserId(String userId);
}
