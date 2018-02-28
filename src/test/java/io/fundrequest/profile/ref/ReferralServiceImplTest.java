package io.fundrequest.profile.ref;

import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.ref.domain.Referral;
import io.fundrequest.profile.ref.infrastructure.ReferralRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReferralServiceImplTest {

    private ReferralRepository referralRepository;
    private KeycloakRepository keycloakRepository;
    private ReferralServiceImpl referralService;

    @Before
    public void setUp() throws Exception {
        referralRepository = mock(ReferralRepository.class);
        keycloakRepository = mock(KeycloakRepository.class);
        referralService = new ReferralServiceImpl(referralRepository, keycloakRepository);
    }

    @Test
    public void createsReferrer() {
        String referrer = "davyvanroy";
        String ref = "ref";

        when(keycloakRepository.userExists(ref)).thenReturn(true);

        referralService.createNewRef(CreateRefCommand.builder().principal(() -> referrer).ref(ref).build());

        ArgumentCaptor<Referral> referralArgumentCaptor = ArgumentCaptor.forClass(Referral.class);
        verify(referralRepository).save(referralArgumentCaptor.capture());
        assertThat(referralArgumentCaptor.getValue().getReferrer()).isEqualTo("davyvanroy");
        assertThat(referralArgumentCaptor.getValue().getReferee()).isEqualTo("ref");
    }
}