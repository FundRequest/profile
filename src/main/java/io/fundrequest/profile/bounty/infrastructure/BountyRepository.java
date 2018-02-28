package io.fundrequest.profile.bounty.infrastructure;

import io.fundrequest.profile.bounty.domain.Bounty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BountyRepository extends JpaRepository<Bounty, Long> {
}
