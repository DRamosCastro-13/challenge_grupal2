package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.repositories.ClientRepository;
import com.mindhub.wireit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> getAllClientDTO() {
        return getAllClient().stream().map(ClientDTO :: new).collect(Collectors.toList());
    }

}
