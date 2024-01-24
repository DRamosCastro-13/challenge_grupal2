package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.newClient;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    //controlador de: client, address, purchaseOrder

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClientDTO() {
        return clientService.getAllClientDTO();
    }

    @GetMapping("/clients/current")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication) {
        ResponseEntity<ClientDTO> response = clientService.getClient(authentication);
        return response;
    }

    @PostMapping("/clients/register")
    public ResponseEntity<String> createClient(@RequestBody newClient newClient){
        ResponseEntity<String> response = clientService.createClient(newClient);
        return response;
    }
}
