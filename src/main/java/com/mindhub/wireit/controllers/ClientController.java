package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.NewAddress;
import com.mindhub.wireit.dto.bodyjson.NewClient;
import com.mindhub.wireit.service.ClientService;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    //controlador de: client, address, purchaseOrder

    @Autowired
    private ClientService clientService;
    @Autowired
    private PdfService pdfService;

    @GetMapping("/clients")
    public List<ClientDTO> getAllClientDTO() {
        return clientService.getAllClientDTO();
    }

    @GetMapping("/clients/current")
    public ResponseEntity<ClientDTO> getClient(Authentication authentication) {
        ResponseEntity<ClientDTO> response = clientService.getClient(authentication);
        return response;
    }

    @PostMapping("/clients")
    public ResponseEntity<String> createClient(@RequestBody NewClient newClient) {
        ResponseEntity<String> response = clientService.createClient(newClient);
        return response;
    }

    @PostMapping("/clients/newaddress")
    public ResponseEntity<String> newAddress(@RequestBody NewAddress newAddress, Authentication authentication) {
        ResponseEntity<String> response = clientService.newAddress(newAddress, authentication);
        return response;
    }


    @GetMapping("/clients/pdf")
    public ResponseEntity<String> generatePDF(@RequestParam String orderNumber,
                                              HttpServletResponse response,
                                              Authentication authentication) throws IOException{
        ResponseEntity<String> responseEnt = pdfService.generatePDF(orderNumber, response, authentication);
        return responseEnt;
    }
}
