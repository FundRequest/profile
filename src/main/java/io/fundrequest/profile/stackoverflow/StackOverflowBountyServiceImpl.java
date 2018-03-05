package io.fundrequest.profile.stackoverflow;

import io.fundrequest.profile.bounty.BountyService;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.provider.Provider;
import io.fundrequest.profile.stackoverflow.domain.StackOverflowBounty;
import io.fundrequest.profile.stackoverflow.dto.StackOverflowResult;
import io.fundrequest.profile.stackoverflow.dto.StackOverflowUser;
import io.fundrequest.profile.stackoverflow.dto.StackOverflowVerificationDto;
import io.fundrequest.profile.stackoverflow.infrastructure.StackOverflowBountyRepository;
import io.fundrequest.profile.stackoverflow.infrastructure.StackOverflowClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.Month;

@Service
class StackOverflowBountyServiceImpl implements StackOverflowBountyService {
    private static final LocalDateTime MIN_PROFILE_DATE = LocalDateTime.of(2018, Month.JANUARY.getValue(), 1, 0, 0, 0, 0);
    private ProfileService profileService;
    private StackOverflowBountyRepository repository;
    private StackOverflowClient client;
    private BountyService bountyService;

    public StackOverflowBountyServiceImpl(ProfileService profileService, StackOverflowBountyRepository repository, StackOverflowClient client, BountyService bountyService) {
        this.profileService = profileService;
        this.repository = repository;
        this.client = client;
        this.bountyService = bountyService;
    }

    @EventListener
    @Transactional
    public void onProviderLinked(UserLinkedProviderEvent event) {
        if (event.getProvider() == Provider.STACKOVERFLOW) {
            UserProfile userProfile = profileService.getUserProfile(null, event.getPrincipal());
            createBountyWhenNecessary(event.getPrincipal(), userProfile);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StackOverflowVerificationDto getVerification(Principal principal) {
        return repository.findByUserId(principal.getName()).map(b -> StackOverflowVerificationDto.builder().approved(b.getValid()).createdAt(b.getCreatedAt()).build())
                .orElse(StackOverflowVerificationDto.builder()
                        .approved(false).build());
    }

    private void createBountyWhenNecessary(Principal principal, UserProfile userProfile) {
        if (!repository.existsByUserId(principal.getName())) {
            StackOverflowResult result = client.getUser(userProfile.getStackoverflow().getUserId());
            if (result != null && result.getUsers() != null && result.getUsers().size() > 0) {
                StackOverflowUser user = result.getUsers().get(0);
                boolean validForBounty = isValidForBounty(user);
                saveGithubBounty(principal, user, validForBounty);
                if (validForBounty) {
                    saveBounty(principal);
                }
            }
        }
    }

    private boolean isValidForBounty(StackOverflowUser user) {
        return user.getCreationDateTime().isBefore(MIN_PROFILE_DATE);
    }

    private void saveBounty(Principal principal) {
        bountyService.createBounty(CreateBountyCommand.builder()
                .userId(principal.getName())
                .type(BountyType.LINK_STACK_OVERFLOW).build());
    }

    private void saveGithubBounty(Principal principal, StackOverflowUser user, boolean validForBounty) {
        StackOverflowBounty bounty = StackOverflowBounty.builder()
                .userId(principal.getName())
                .createdAt(user.getCreationDateTime())
                .displayName(user.getDisplayName())
                .image(user.getProfileImage())
                .reputation(user.getReputation())
                .stackOverflowId(user.getUserId())
                .valid(validForBounty)
                .build();
        repository.save(bounty);
    }
}
