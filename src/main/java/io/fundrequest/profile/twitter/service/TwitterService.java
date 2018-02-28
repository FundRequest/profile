package io.fundrequest.profile.twitter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

@Service
@Slf4j
public class TwitterService {

    @Autowired
    private Twitter twitter;

    public boolean userIsFollowing(final String user) {
        try {
            return twitter.friendsFollowers().showFriendship(user, "fundrequest_io").isSourceFollowingTarget();
        } catch (final Exception ex) {
            log.error("It would appear as if {} does not exist", user);
            return false;
        }
    }


}
