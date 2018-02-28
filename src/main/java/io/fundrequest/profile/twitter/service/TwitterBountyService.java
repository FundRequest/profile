package io.fundrequest.profile.twitter.service;

import io.fundrequest.profile.twitter.model.TwitterBounty;
import io.fundrequest.profile.twitter.repository.TwitterBountyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

import java.util.Optional;

@Service
@Slf4j
public class TwitterBountyService {

    @Autowired
    private TwitterBountyRepository twitterBountyRepository;
    @Autowired
    private Twitter twitter;

    public Optional<TwitterBounty> findById() {
        //try
        return Optional.empty();
    }

    public boolean userIsFollowing(final String user) {
        try {
            return twitter.friendsFollowers().showFriendship(user, "fundrequest_io").isSourceFollowingTarget();
        } catch (final Exception ex) {
            log.error("It would appear as if {} does not exist", user);
            return false;
        }
    }

    public boolean hasFullFilled(final String username, final Long bountyId) {
        return false;
    }
}
