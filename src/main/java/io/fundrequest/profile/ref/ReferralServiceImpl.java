package io.fundrequest.profile.ref;

import io.fundrequest.profile.bounty.BountyService;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.ref.domain.Referral;
import io.fundrequest.profile.ref.domain.ReferralStatus;
import io.fundrequest.profile.ref.dto.ReferralDto;
import io.fundrequest.profile.ref.dto.ReferralOverviewDto;
import io.fundrequest.profile.ref.infrastructure.ReferralRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @EventListener
    @Transactional
    public void onProviderLinked(UserLinkedProviderEvent event) {
        if (event.getPrincipal() != null) {
            repository.findByReferee(event.getPrincipal().getName())
                    .ifPresent(r -> sendBountyIfPossible(r, event.getPrincipal()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReferralOverviewDto getOverview(Principal principal) {
        Map<ReferralStatus, List<Referral>> byStatus = repository.findByReferrer(principal.getName()).stream().collect(Collectors.groupingBy(Referral::getStatus));
        return ReferralOverviewDto.builder()
                .totalVerified(byStatus.get(ReferralStatus.VERIFIED).size())
                .totalUnverified(byStatus.get(ReferralStatus.PENDING).size())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReferralDto> getReferrals(Principal principal) {
        return repository.findByReferrer(principal.getName(), new Sort(Sort.Direction.DESC, "creationDate"))
                .stream()
                .parallel()
                .map(this::createReferralDto)
                .collect(Collectors.toList());
    }

    private ReferralDto createReferralDto(Referral r) {
        UserRepresentation ur = keycloakRepository.getUser(r.getReferee());
        return ReferralDto.builder().status(r.getStatus())
                .name(ur.getFirstName() + " " + ur.getLastName())
                .email(ur.getEmail())
                .picture(keycloakRepository.getAttribute(ur, "picture"))
                .createdAt(r.getCreationDate()).build();
    }

    @Override
    @Transactional
    public void createNewRef(@Valid CreateRefCommand command) {
        String referrer = command.getRef();
        String referee = command.getPrincipal().getName();
        validReferral(referrer, referee);
        if (!repository.existsByReferee(referee)) {
            Referral referral = Referral.builder()
                    .referrer(referrer)
                    .referee(referee)
                    .status(ReferralStatus.PENDING)
                    .build();
            repository.save(referral);
            sendBountyIfPossible(referral, command.getPrincipal());

        } else {
            throw new RuntimeException("This ref already exists!");
        }
    }

    private void sendBountyIfPossible(Referral referral, Principal principal) {
        if (isVerifiedPrincipal(principal)) {
            referral.setStatus(ReferralStatus.VERIFIED);
            bountyService.createBounty(CreateBountyCommand.builder()
                    .userId(principal.getName())
                    .type(REFERRAL)
                    .build());
            repository.save(referral);
        }
    }

    private boolean isVerifiedPrincipal(Principal principal) {
        return keycloakRepository.isVerifiedDeveloper(principal.getName());
    }

    private void validReferral(String referrer, String referee) {
        if (isValidReferee(referrer, referee)) {
            throw new RuntimeException("This is not a valid referee");
        }
        if (!keycloakRepository.userExists(referrer)) {
            throw new RuntimeException("This is not a valid referrer");
        }
    }

    private boolean isValidReferee(String referrer, String referee) {
        return referrer.equalsIgnoreCase(referee) ||
                !keycloakRepository.userExists(referee);
    }
}
