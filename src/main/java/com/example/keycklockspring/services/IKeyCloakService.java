package com.example.keycklockspring.services;

import com.example.keycklockspring.DTO.OrgDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeyCloakService {
    void addUser(OrgDTO orgDTO) throws Exception;

    void addOrganiation() throws Exception;

    List<UserRepresentation> getUser(String userName);

    void deleteUser(String userId);
}
