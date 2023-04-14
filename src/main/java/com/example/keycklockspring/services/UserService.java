package com.example.keycklockspring.services;

import com.example.keycklockspring.DTO.OrgDTO;
import com.example.keycklockspring.DTO.UserDTO;
import com.example.keycklockspring.KeycklockConfig;
import io.phasetwo.client.*;
import io.phasetwo.client.openapi.model.InvitationRepresentation;
import io.phasetwo.client.openapi.model.OrganizationRepresentation;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;


@Service
public class UserService {
    @Autowired
    KeycklockConfig keycklockConfig;

    public String addUser(UserDTO userDTO) {
        Keycloak keycloak = keycklockConfig.getInstance();
        PhaseTwo phaseTwo = new PhaseTwo(keycloak, keycklockConfig.getSERVER_URL());
        UsersResource usersResource = keycloak.realm(keycklockConfig.getREALM()).users();
        CredentialRepresentation pass = new CredentialRepresentation();
        pass.setType(CredentialRepresentation.PASSWORD);
        pass.setValue(userDTO.getPassword());
        pass.setTemporary(false);
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);

        Response response = usersResource.create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);

        OrganizationsResource orgsResource = phaseTwo.organizations(keycklockConfig.getREALM());
        String orgId = userDTO.getOrgId();
        orgsResource.organization(orgId).memberships().add(userId);



        return userId;
    }}


