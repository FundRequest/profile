package io.fundrequest.profile.ref;

import io.fundrequest.profile.bounty.service.BountyService;
import io.fundrequest.profile.bounty.event.CreateBountyCommand;
import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.ref.domain.Referral;
import io.fundrequest.profile.ref.infrastructure.ReferralRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static io.fundrequest.profile.bounty.domain.BountyType.REFERRAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReferralServiceImplTest {

    private ReferralRepository referralRepository;
    private KeycloakRepository keycloakRepository;
    private ReferralServiceImpl referralService;
    private BountyService bountyService;

    @Before
    public void setUp() throws Exception {
        referralRepository = mock(ReferralRepository.class);
        keycloakRepository = mock(KeycloakRepository.class);
        bountyService = mock(BountyService.class);
        referralService = new ReferralServiceImpl(referralRepository, keycloakRepository, bountyService);
    }

    @Test
    @Ignore
    public void createsReferrer() {
        String referrer = "davyvanroy";
        String ref = "ref";

        when(keycloakRepository.userExists(ref)).thenReturn(true);

        referralService.createNewRef(CreateRefCommand.builder().principal(() -> referrer).ref(ref).build());

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        verify(referralRepository).save(referralArgumentCaptor.capture());
        assertThat(referralArgumentCaptor.getValue().getReferrer()).isEqualTo("davyvanroy");
        assertThat(referralArgumentCaptor.getValue().getReferee()).isEqualTo("ref");

        verify(bountyService).createBounty(CreateBountyCommand.builder().userId("davyvanroy").type(REFERRAL).build());
    }
}