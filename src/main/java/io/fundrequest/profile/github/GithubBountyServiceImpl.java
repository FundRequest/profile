package io.fundrequest.profile.github;

import io.fundrequest.profile.bounty.BountyService;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.github.domain.GithubBounty;
import io.fundrequest.profile.github.infrastructure.GithubBountyRepository;
import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.provider.Provider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class GithubBountyServiceImpl implements GithubBountyService, ApplicationListener<AuthenticationSuccessEvent> {

    private ProfileService profileService;
    private GithubBountyRepository githubBountyRepository;
    private BountyService bountyService;

    public GithubBountyServiceImpl(ProfileService profileService, GithubBountyRepository githubBountyRepository, BountyService bountyService) {
        this.profileService = profileService;
        this.githubBountyRepository = githubBountyRepository;
        this.bountyService = bountyService;
    }

    @EventListener
    public void onProviderLinked(UserLinkedProviderEvent event) {
        if (event.getProvider() == Provider.GITHUB) {
            createBountyWhenNecessary(event.getPrincipal());
        }
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication principal = event.getAuthentication();
        UserProfile userProfile = profileService.getUserProfile(null, principal);
        if (userProfile.getGithub() != null && StringUtils.isNotBlank(userProfile.getGithub().getUsername())) {
            createBountyWhenNecessary(principal);
        }
    }

    private void createBountyWhenNecessary(Principal principal) {
        if (!githubBountyRepository.existsByUserId(principal.getName())) {
            saveGithubBounty(principal);
            saveBounty(principal);
        }
    }

    private void saveBounty(Principal principal) {
        bountyService.createBounty(CreateBountyCommand.builder()
                .userId(principal.getName())
                .type(BountyType.LINK_GITHUB).build());
    }

    private void saveGithubBounty(Principal principal) {
        GithubBounty githubBounty = GithubBounty.builder()
                .userId(principal.getName())
                .githubId(profileService.getUserProfile(null, principal).getGithub().getUsername())
                .build();
        githubBountyRepository.save(githubBounty);
    }
}
