package io.fundrequest.profile.ref;

import io.fundrequest.profile.bounty.BountyService;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.ref.domain.Referral;
import io.fundrequest.profile.ref.infrastructure.ReferralRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import static io.fundrequest.profile.bounty.domain.BountyType.REFERRAL;

@Service
class ReferralServiceImpl implements ReferralService {

    private ReferralRepository repository;
    private KeycloakRepository keycloakRepository;
    private BountyService bountyService;

    public ReferralServiceImpl(ReferralRepository repository, KeycloakRepository keycloakRepository, BountyService bountyService) {
        this.repository = repository;
        this.keycloakRepository = keycloakRepository;
        this.bountyService = bountyService;
    }

    @Override
    @Transactional
    public void createNewRef(@Valid CreateRefCommand command) {
        String referrer = command.getPrincipal().getName();
        String referee = command.getRef();
        validateReferee(referrer, referee);
        if (!repository.existsByReferrerAndReferee(referrer, referee)) {
            Referral referral = Referral.builder()
                    .referrer(referrer)
                    .referee(referee)
                    .build();
            repository.save(referral);
            bountyService.createBounty(CreateBountyCommand.builder()
                    .userId(command.getPrincipal().getName())
                    .type(REFERRAL)
                    .build());

        } else {
            throw new RuntimeException("This ref already exists!");
        }
    }

    private void validateReferee(String referrer, String referee) {
        if (isValidReferee(referrer, referee)) {
            throw new RuntimeException("this is not a valid referee");
        }
    }

    private boolean isValidReferee(String referrer, String referee) {
        return referrer.equalsIgnoreCase(referee) ||
                !keycloakRepository.userExists(referee);
    }
}
