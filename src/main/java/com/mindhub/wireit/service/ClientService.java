package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.repositories.ClientRepository;

import java.util.List;

public interface ClientService {

    List<Client> getAllClient();

    List<ClientDTO> getAllClientDTO();
}
