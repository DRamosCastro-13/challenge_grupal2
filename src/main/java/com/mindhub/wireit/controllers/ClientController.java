package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    //controlador de: client, address, purchaseOrder

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClientDTO(){
        return clientService.getAllClientDTO();
    }

}
