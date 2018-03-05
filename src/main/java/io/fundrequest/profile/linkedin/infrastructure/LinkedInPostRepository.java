package io.fundrequest.profile.linkedin.infrastructure;

import io.fundrequest.profile.linkedin.domain.LinkedInPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedInPostRepository extends JpaRepository<LinkedInPost, Long> {
}
