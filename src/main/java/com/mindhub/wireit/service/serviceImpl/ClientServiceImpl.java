package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.NewClient;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.repositories.ClientRepository;
import com.mindhub.wireit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> getAllClientDTO() {
        return getAllClient().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ClientDTO> getClient(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        ResponseEntity response;
        if (client != null) {
            ClientDTO clientDTO = new ClientDTO(client);
            response = new ResponseEntity<>(clientDTO, HttpStatus.OK);
            return response;
        } else {
            return response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> createClient(NewClient newClient) {
        if (newClient.getFirstName().isBlank()) {
            return new ResponseEntity<>("Your name can not be blank.", HttpStatus.FORBIDDEN);
        }
        if (newClient.getLastName().isBlank()) {
            return new ResponseEntity<>("Your lastname can not be blank.", HttpStatus.FORBIDDEN);
        }
        if (newClient.getDni() <= 0){
            return new ResponseEntity<>("Your dni can not be 0.", HttpStatus.FORBIDDEN);
        }
        if (newClient.getEmail().isBlank()){
            return new ResponseEntity<>("Your email can not be blank.", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(newClient.getEmail()) != null){
            return new ResponseEntity<>("Email address already in use.",HttpStatus.FORBIDDEN);
        }
        if (newClient.getPassword().isBlank()){
            return new ResponseEntity<>("Your password can not be blank", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(newClient.getFirstName(), newClient.getLastName(), newClient.getEmail(), newClient.getDni(),passwordEncoder.encode(newClient.getPassword()));
        clientRepository.save(client);

        return new ResponseEntity<>("Registration successful. Your account is now active.",HttpStatus.CREATED);
    }


}
