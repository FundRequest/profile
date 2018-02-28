package io.fundrequest.profile.twitter.service;

import io.fundrequest.profile.twitter.model.TwitterBounty;
import io.fundrequest.profile.twitter.model.TwitterBountyType;
import io.fundrequest.profile.twitter.model.TwitterPost;
import io.fundrequest.profile.twitter.repository.TwitterBountyRepository;
import io.fundrequest.profile.twitter.repository.TwitterPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

import java.util.List;

@Service
@Slf4j
public class TwitterBountyService {

    @Autowired
    private TwitterBountyRepository twitterBountyRepository;
    @Autowired
    private Twitter twitter;
    @Autowired
    private TwitterPostRepository twitterPostRepository;

    public boolean userIsFollowing(final String user) {
        try {
            return twitter.friendsFollowers().showFriendship(user, "fundrequest_io").isSourceFollowingTarget();
        } catch (final Exception ex) {
            log.error("It would appear as if {} does not exist", user);
            return false;
        }
    }

    public boolean hasFullFilled(final String username, final Long bountyId) {
        final TwitterBounty bounty = twitterBountyRepository.findOne(bountyId);
        if (bounty != null) {
            return hasFullFilled(username, bounty);
        } else {
            return false;
        }
    }

    public boolean hasFullFilled(final String username, final TwitterBounty bounty) {
        if (bounty.getType().equals(TwitterBountyType.TWEET)) {
            return validateTweets(username, bounty);
        } else {
            return false;
        }
    }

    private boolean validateTweets(final String username, final TwitterBounty bounty) {
        final List<TwitterPost> posts = twitterPostRepository.findAll();
        try {
            boolean fulfillled = twitter.timelines().getUserTimeline(username)
                    .stream()
                    .anyMatch(status -> posts.stream()
                            .anyMatch(post -> status.getText().contains(post.getVerificationText())));
            if (fulfillled) {
                fulfillBounty(username, bounty);
            }
            return fulfillled;
        } catch (final Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void fulfillBounty(final String username, final TwitterBounty bounty) {


    }
}
