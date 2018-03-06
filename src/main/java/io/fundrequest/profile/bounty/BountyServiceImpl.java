package io.fundrequest.profile.bounty;

import io.fundrequest.profile.bounty.domain.Bounty;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.dto.BountyDTO;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.bounty.infrastructure.BountyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
class BountyServiceImpl implements BountyService {

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
        return BountyDTO.builder()
                .bounties(byUser)
                .referralCount(
                        byUser
                                .stream()
                                .filter(x -> x.getType().equals(BountyType.REFERRAL))
                                .count(
                                ))
                .build();
    }


}
