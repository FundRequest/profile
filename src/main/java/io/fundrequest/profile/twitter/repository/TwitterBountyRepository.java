package io.fundrequest.profile.twitter.repository;

import io.fundrequest.profile.twitter.model.TwitterBounty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterBountyRepository extends JpaRepository<TwitterBounty, Long> {
}
