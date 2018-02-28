package io.fundrequest.profile.twitter.repository;


import io.fundrequest.profile.twitter.model.TwitterPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterPostRepository extends JpaRepository<TwitterPost, Long> {
}
