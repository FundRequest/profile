package io.fundrequest.profile.ref.infrastructure;

import io.fundrequest.profile.ref.domain.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    boolean existsByReferrerAndReferee(String referrer, String referee);
}
