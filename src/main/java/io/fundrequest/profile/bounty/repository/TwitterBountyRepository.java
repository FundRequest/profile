package io.fundrequest.profile.bounty.repository;

import io.fundrequest.profile.bounty.model.TwitterBounty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterBountyRepository extends JpaRepository<TwitterBounty, Long> {
}
