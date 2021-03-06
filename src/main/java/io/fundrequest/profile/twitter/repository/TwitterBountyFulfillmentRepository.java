package io.fundrequest.profile.twitter.repository;


import io.fundrequest.profile.twitter.model.TwitterBounty;
import io.fundrequest.profile.twitter.model.TwitterBountyFulfillment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TwitterBountyFulfillmentRepository extends JpaRepository<TwitterBountyFulfillment, Long> {


    Optional<TwitterBountyFulfillment> findByUserIdAndBounty(@Param("userId") final String userId, @Param("bounty") final TwitterBounty bounty);

}
