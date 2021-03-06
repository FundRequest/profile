package io.fundrequest.profile.bounty.infrastructure;

import io.fundrequest.profile.bounty.domain.Bounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BountyRepository extends JpaRepository<Bounty, Long> {
    List<Bounty> findByUserId(final @Param("userId") String userId);
}
