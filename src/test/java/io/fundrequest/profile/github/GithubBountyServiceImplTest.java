package io.fundrequest.profile.github;

import io.fundrequest.profile.bounty.service.BountyService;
import io.fundrequest.profile.bounty.domain.BountyType;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.github.domain.GithubBounty;
import io.fundrequest.profile.github.dto.GithubUser;
import io.fundrequest.profile.github.infrastructure.GithubBountyRepository;
import io.fundrequest.profile.github.infrastructure.GithubClient;
import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.dto.UserProfileProvider;
import io.fundrequest.profile.profile.provider.Provider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubBountyServiceImplTest {

    private GithubBountyServiceImpl githubBountyService;
    private ProfileService profileService;
    private GithubBountyRepository githubBountyRepository;
    private BountyService bountyService;
    private GithubClient githubClient;
    private ApplicationEventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        profileService = mock(ProfileService.class);
        githubBountyRepository = mock(GithubBountyRepository.class);
        bountyService = mock(BountyService.class);
        githubClient = mock(GithubClient.class);
        eventPublisher = mock(ApplicationEventPublisher.class);
        githubBountyService = new GithubBountyServiceImpl(
                profileService,
                githubBountyRepository,
                bountyService,
                githubClient, eventPublisher);
    }

    @Test
    public void onAuthenticationChecksGithubSignup() {
        Authentication authentication = mock(Authentication.class, RETURNS_DEEP_STUBS);
        when(authentication.getName()).thenReturn("davy");
        when(profileService.getUserProfile(null, authentication))
                .thenReturn(UserProfile.builder().github(UserProfileProvider.builder().userId("id").username("davy").build()).verifiedDeveloper(true).build());
        when(githubClient.getUser("davy")).thenReturn(GithubUser.builder().createdAt(LocalDateTime.now().minusMonths(3)).location("Belgium").build());

        githubBountyService.onApplicationEvent(new AuthenticationSuccessEvent(authentication));

        verifyBountiesSaved();
    }

    @Test
    public void onProviderLinked() {
        Authentication authentication = mock(Authentication.class, RETURNS_DEEP_STUBS);
        when(authentication.getName()).thenReturn("davy");
        when(profileService.getUserProfile(null, authentication))
                .thenReturn(UserProfile.builder().github(UserProfileProvider.builder().userId("id").username("davy").build()).verifiedDeveloper(true).build());
        when(githubClient.getUser("davy")).thenReturn(GithubUser.builder().createdAt(LocalDateTime.now().minusMonths(3)).location("Belgium").build());

        githubBountyService.onProviderLinked(UserLinkedProviderEvent.builder().principal(authentication).provider(Provider.GITHUB).build());

        verifyBountiesSaved();
    }

    private void verifyBountiesSaved() {
        verify(githubBountyRepository).save(GithubBounty.builder().githubId("id").userId("davy").build());
        verify(bountyService).createBounty(
                CreateBountyCommand.builder()
                        .type(BountyType.LINK_GITHUB)
                        .userId("davy")
                        .build());
    }
}