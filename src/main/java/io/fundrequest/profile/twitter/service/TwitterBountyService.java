package io.fundrequest.profile.twitter.service;

import io.fundrequest.profile.twitter.model.TwitterBounty;
import io.fundrequest.profile.twitter.model.TwitterBountyFulfillment;
import io.fundrequest.profile.twitter.model.TwitterBountyType;
import io.fundrequest.profile.twitter.model.TwitterPost;
import io.fundrequest.profile.twitter.repository.TwitterBountyFulfillmentRepository;
import io.fundrequest.profile.twitter.repository.TwitterBountyRepository;
import io.fundrequest.profile.twitter.repository.TwitterPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TwitterBountyService {

    @Autowired
    private TwitterBountyRepository twitterBountyRepository;
    @Autowired
    private Twitter twitter;
    @Autowired
    private TwitterPostRepository twitterPostRepository;
    @Autowired
    private TwitterBountyFulfillmentRepository twitterBountyFulfillmentRepository;

    public List<TwitterPost> getTwitterPosts() {
        return twitterPostRepository.findAll();
    }

    public Optional<TwitterBounty> getActiveBounty() {
        List<TwitterBounty> active = twitterBountyRepository.getActiveTwitterBounties(new Date());
        if (active.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(active.get(0));
        }
    }

    public boolean userIsFollowing(final String user) {
        try {
            return twitter.friendsFollowers().showFriendship(user, "fundrequest_io").isSourceFollowingTarget();
        } catch (final Exception ex) {
            log.error("It would appear as if {} does not exist", user);
            return false;
        }
    }

    public boolean hasFullFilled(final String username, final String userId, final Long bountyId) {
        final TwitterBounty bounty = twitterBountyRepository.findOne(bountyId);
        return bounty != null && hasFullFilled(username, userId, bounty);
    }

    public boolean hasFullFilled(final String username, final String userId, final TwitterBounty bounty) {
        if (bounty.getType().equals(TwitterBountyType.TWEET)) {
            return validateTweets(username, userId, bounty);
        } else {
            return false;
        }
    }

    private boolean validateTweets(final String username, final String userId, final TwitterBounty bounty) {
        final List<TwitterPost> posts = twitterPostRepository.findAll();
        try {
            boolean fulfillled = twitter.timelines().getUserTimeline(username)
                    .stream()
                    .anyMatch(status -> posts.stream()
                            .anyMatch(post -> status.getText().contains(post.getVerificationText())));
            if (fulfillled) {
                fulfillBounty(username, userId, bounty);
            }
            return fulfillled;
        } catch (final Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void fulfillBounty(final String username, final String userId, final TwitterBounty bounty) {
        final boolean alreadyAdded = claimedBountyAlready(userId, bounty);
        if (!alreadyAdded) {
            final TwitterBountyFulfillment newFullfillment = new TwitterBountyFulfillment();
            newFullfillment.setBounty(bounty);
            newFullfillment.setFulfillmentDate(new Date());
            newFullfillment.setUserId(userId);
            newFullfillment.setUsername(username);
            twitterBountyFulfillmentRepository.save(
                    newFullfillment
            );
        }
    }

    public boolean claimedBountyAlready(final String userId, final TwitterBounty bounty) {
        return twitterBountyFulfillmentRepository.findByUserIdAndBounty(userId, bounty).isPresent();
    }
}
