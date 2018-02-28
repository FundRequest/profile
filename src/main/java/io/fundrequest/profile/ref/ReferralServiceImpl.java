package io.fundrequest.profile.ref;

import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.ref.domain.Referral;
import io.fundrequest.profile.ref.infrastructure.ReferralRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
class ReferralServiceImpl implements ReferralService {

    private ReferralRepository repository;
    private KeycloakRepository keycloakRepository;

    public ReferralServiceImpl(ReferralRepository repository, KeycloakRepository keycloakRepository) {
        this.repository = repository;
        this.keycloakRepository = keycloakRepository;
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
