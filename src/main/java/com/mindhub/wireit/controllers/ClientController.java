package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.NewAddress;
import com.mindhub.wireit.dto.bodyjson.NewClient;

import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.service.ClientService;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
                                              Authentication authentication) throws IOException {
        // Obtener el cliente autenticado
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        // Obtener las órdenes de compra del cliente
        Set<PurchaseOrder> purchaseOrders = client.getPurchaseOrders();

        for (PurchaseOrder po : purchaseOrders) {
            if (po.getOrderNumber().equals(orderNumber)) {
                // Verificar si el correo electrónico del cliente autenticado coincide con el correo asociado a la orden
                if (authentication.getName().equals(po.getClient().getEmail())) {
                    response.setContentType("application/pdf");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                    String currentDateTime = dateFormat.format(new Date());
                    String headerKey = "Content-Disposition";
                    String headerValue = "attachment; filename=order_" + currentDateTime + ".pdf";
                    response.setHeader(headerKey, headerValue);
                    pdfService.export(response, orderNumber);
                    return new ResponseEntity<>("PDF generated successfully.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Unauthorized: Email mismatch for the specified order.", HttpStatus.UNAUTHORIZED);
                }
            }
        }
        // Si llega aquí, significa que no se encontró la orden con el número especificado
        return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
    }
}
