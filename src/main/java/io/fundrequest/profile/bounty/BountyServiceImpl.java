package io.fundrequest.profile.bounty;

import io.fundrequest.profile.bounty.domain.Bounty;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.dto.BountyDTO;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.bounty.infrastructure.BountyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class BountyServiceImpl implements BountyService {

    private static final int REF_LINK_REWARD = 5;
    private BountyRepository bountyRepository;

    public BountyServiceImpl(BountyRepository bountyRepository) {
        this.bountyRepository = bountyRepository;
    }

    @Transactional
    @Override
    public void createBounty(CreateBountyCommand createBountyCommand) {
        final Bounty bounty = Bounty.builder()
                .userId(createBountyCommand.getUserId())
                .type(createBountyCommand.getType())
                .build();

        bountyRepository.save(bounty);
    }

    @Override
    public BountyDTO getBounties(final Principal principal) {
        final List<Bounty> byUser = bountyRepository.findByUserId(principal.getName());
        Map<BountyType, List<Bounty>> byType = byUser.stream()
                .collect(Collectors.groupingBy(Bounty::getType));

        int referralRewards = byType.getOrDefault(BountyType.REFERRAL, new ArrayList<>()).size() * REF_LINK_REWARD;
        int twitterRewards = byType.getOrDefault(BountyType.TWITTER_TWEET_FOLLOW, new ArrayList<>()).size() * 10;
        int linkedInRewards = byType.getOrDefault(BountyType.POST_LINKEDIN_UPDATE, new ArrayList<>()).size() * 10;
        int telegramRewards = byType.getOrDefault(BountyType.LINK_TELEGRAM, new ArrayList<>()).size() * 5;
        int otherRewards =
                byType.get(BountyType.LINK_GITHUB).size() * 15
                        + byType.getOrDefault(BountyType.LINK_STACK_OVERFLOW, new ArrayList<>()).size() * 15
                        + linkedInRewards
                        + twitterRewards
                        + telegramRewards;

        return BountyDTO.builder()
                .referralRewards(referralRewards)
                .otherRewards(otherRewards)
                .totalRewards(referralRewards + otherRewards)
                .twitterRewards(twitterRewards)
                .linkedInRewards(linkedInRewards)
                .telegramRewards(telegramRewards)
                .build();
    }


}
