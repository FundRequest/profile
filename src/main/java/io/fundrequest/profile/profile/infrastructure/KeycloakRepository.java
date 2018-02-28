package io.fundrequest.profile.profile.infrastructure;

import io.fundrequest.profile.profile.dto.UserIdentity;
import io.fundrequest.profile.profile.provider.Provider;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class KeycloakRepository {

    private RealmResource resource;

    public KeycloakRepository(RealmResource resource) {
        this.resource = resource;
    }


    public Stream<UserIdentity> getUserIdentities(String userId) {
        return resource.users().get(userId).getFederatedIdentity()
                .stream()
                .map(fi -> UserIdentity.builder().provider(Provider.fromString(fi.getIdentityProvider())).username(fi.getUserName()).userId(fi.getUserId()).build());
    }

    public void updateEtherAddress(String userId, String etherAddress) {
        UserResource userResource = resource.users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation.getAttributes().put("ether_address", Collections.singletonList(etherAddress));
        userResource.update(userRepresentation);
    }

    public String getEtherAddress(String userId) {
        Map<String, List<String>> attributes = resource.users().get(userId).toRepresentation().getAttributes();
        if (attributes != null && attributes.size() > 0) {
            List<String> etherAddresses = attributes.get("ether_address");
            if (etherAddresses != null && etherAddresses.size() > 0) {
                return etherAddresses.get(0);
            }
        }
        return null;
    }

    public boolean userExists(String userId) {
        try {
            return resource.users().get(userId).toRepresentation() != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

}
