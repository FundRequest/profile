package io.fundrequest.profile.ref;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RefSignupListenerTest {

    private RefSignupListener refSignupListener;
    private ReferralService referralService;

    @Before
    public void setUp() throws Exception {
        referralService = mock(ReferralService.class);
        refSignupListener = new RefSignupListener(referralService);
    }

    @Test
    public void createsEvent() {
        refSignupListener.onSignup(RefSignupEvent.builder().principal(() -> "davyvanroy").ref("ref").build());

        ArgumentCaptor<CreateRefCommand> createRefCommandArgumentCaptor = ArgumentCaptor.forClass(CreateRefCommand.class);
        verify(referralService).createNewRef(createRefCommandArgumentCaptor.capture());
        assertThat(createRefCommandArgumentCaptor.getValue().getPrincipal().getName()).isEqualTo("davyvanroy");
        assertThat(createRefCommandArgumentCaptor.getValue().getRef()).isEqualTo("ref");
    }
}