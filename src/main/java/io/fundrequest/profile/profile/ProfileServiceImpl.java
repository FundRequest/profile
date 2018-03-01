package io.fundrequest.profile.profile;

import io.fundrequest.profile.profile.dto.UserIdentity;
import io.fundrequest.profile.profile.dto.UserLinkedProviderEvent;
import io.fundrequest.profile.profile.dto.UserProfile;
import io.fundrequest.profile.profile.dto.UserProfileProvider;
import io.fundrequest.profile.profile.infrastructure.KeycloakRepository;
import io.fundrequest.profile.profile.provider.Provider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.common.util.Base64Url;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private KeycloakRepository keycloakRepository;
    private String keycloakUrl;
    private ApplicationEventPublisher eventPublisher;

    public ProfileServiceImpl(KeycloakRepository keycloakRepository, @Value("${io.fundrequest.keycloak-custom.server-url}") String keycloakUrl, ApplicationEventPublisher eventPublisher) {
        this.keycloakRepository = keycloakRepository;
        this.keycloakUrl = keycloakUrl;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void userProviderIdentityLinked(Principal principal, Provider provider) {
        eventPublisher.publishEvent(UserLinkedProviderEvent.builder().principal(principal).provider(provider).build());
    }

    @Override
    public UserProfile getUserProfile(HttpServletRequest request, Principal principal) {
        IDToken idToken = ((KeycloakAuthenticationToken) principal).getAccount().getKeycloakSecurityContext().getIdToken();
        Map<Provider, UserProfileProvider> providers = keycloakRepository.getUserIdentities(principal.getName()).collect(Collectors.toMap(UserIdentity::getProvider, x -> UserProfileProvider.builder().userId(x.getUserId()).username(x.getUsername()).build()));
        if (request != null) {
            addMissingProviders(request, principal, providers);
        }
        return UserProfile.builder()
                .name(idToken.getName())
                .email(idToken.getEmail())
                .etherAddress(keycloakRepository.getEtherAddress(principal.getName()))
                .github(providers.get(Provider.GITHUB))
                .linkedin(providers.get(Provider.LINKEDIN))
                .twitter(providers.get(Provider.TWITTER))
                .google(providers.get(Provider.GOOGLE))
                .stackoverflow(providers.get(Provider.STACKOVERFLOW))
                .build();
    }

    private UserProfileProvider createUserProfileProvider(UserIdentity ui) {
        return UserProfileProvider.builder().userId(ui.getUserId()).username(ui.getUsername()).build();
    }

    @Override
    public void updateEtherAddress(Principal principal, String etherAddress) {
        keycloakRepository.updateEtherAddress(principal.getName(), etherAddress);
    }

    private void addMissingProviders(HttpServletRequest request, Principal principal, Map<Provider, UserProfileProvider> providers) {
        for (Provider provider : Provider.values()) {
            addProviderToProviders(request, principal, providers, provider);
        }
    }

    private void addProviderToProviders(HttpServletRequest request, Principal principal, Map<Provider, UserProfileProvider> providers, Provider provider) {
        if (!providers.containsKey(provider)) {
            providers.put(
                    provider,
                    UserProfileProvider.builder()
                            .signupLink(createLink(request, principal, provider.toString().toLowerCase()))
                            .build()
            );
        }
    }

    private String createLink(HttpServletRequest request, Principal principal, String provider) {
        AccessToken token = ((KeycloakAuthenticationToken) principal).getAccount().getKeycloakSecurityContext().getToken();
        String clientId = token.getIssuedFor();
        String nonce = UUID.randomUUID().toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String input = nonce + token.getSessionState() + clientId + provider;
        byte[] check = md.digest(input.getBytes(StandardCharsets.UTF_8));
        String hash = Base64Url.encode(check);
        request.getSession().setAttribute("hash", hash);
        return KeycloakUriBuilder.fromUri(keycloakUrl)
                .path("/realms/{realm}/broker/{provider}/link")
                .queryParam("nonce", nonce)
                .queryParam("hash", hash)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", getRedirectUrl(request, provider)).build("fundrequest", provider).toString();
    }

    private String getRedirectUrl(HttpServletRequest req, String provider) {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        String url = scheme + "://" + serverName;

        if (serverPort != 80 && serverPort != 443) {
            url += ":" + serverPort;
        }
        url += contextPath;
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += "profile/link/" + provider + "/redirect";
        return url;
    }
}
