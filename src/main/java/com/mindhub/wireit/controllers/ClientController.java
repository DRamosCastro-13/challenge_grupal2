package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.bodyjson.NewAddress;
import com.mindhub.wireit.dto.bodyjson.NewClient;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.service.ClientService;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<byte[]> generatePDF(@RequestParam String orderNumber,
                                              Authentication authentication,
                                              HttpServletResponse response) throws IOException {
        // Asegúrate de que la autenticación no sea nula
        if (authentication == null || authentication.getName() == null) {
            return new ResponseEntity<>("Authentication is null or name is null.".getBytes(), HttpStatus.FORBIDDEN);
        }

        // Obtén el cliente autenticado
        Client client = clientService.getAuthenticatedClient(authentication.getName());

        // Asegúrate de que el cliente no sea nulo
        if (client == null) {
            return new ResponseEntity<>("Client is null.".getBytes(), HttpStatus.FORBIDDEN);
        }

        List<PurchaseOrder> purchaseOrders = client.getPurchaseOrders();

        for (PurchaseOrder po : purchaseOrders) {
            if (po.getOrderNumber().equals(orderNumber)) {
                // Verifica si el correo electrónico del cliente autenticado coincide con el correo asociado a la orden
                if (authentication.getName().equals(po.getClient().getEmail())) {
                    // Intenta exportar el PDF
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = pdfService.export(response, orderNumber);

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_PDF);
                        headers.setContentDispositionFormData("attachment", "order_" + orderNumber + ".pdf");

                        // Devuelve el contenido del ByteArrayOutputStream como un array de bytes
                        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
                    } catch (Exception e) {
                        // Imprime el error para ayudar a depurar
                        e.printStackTrace();
                    }
                } else {
                    return new ResponseEntity<>("Unauthorized: Email mismatch for the specified order.".getBytes(), HttpStatus.UNAUTHORIZED);
                }
            }
        }
        // Si llegas aquí, significa que no se encontró la orden con el número especificado
        return new ResponseEntity<>("Order not found.".getBytes(), HttpStatus.NOT_FOUND);
    }

}
