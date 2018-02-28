package io.fundrequest.profile.bounty;

import io.fundrequest.profile.bounty.domain.Bounty;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.bounty.infrastructure.BountyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class BountyServiceImpl implements BountyService {

    private BountyRepository bountyRepository;

    public BountyServiceImpl(BountyRepository bountyRepository) {
        this.bountyRepository = bountyRepository;
    }

    @Transactional
    @Override
    public void createBounty(CreateBountyCommand createBountyCommand) {
        Bounty bounty = Bounty.builder()
                .userId(createBountyCommand.getUserId())
                .type(createBountyCommand.getType())
                .build();

        bountyRepository.save(bounty);
    }
}
