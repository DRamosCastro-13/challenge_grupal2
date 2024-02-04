package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.Utils.CalculateTotalAmount;
import com.mindhub.wireit.Utils.OrderNumberGenerator;
import com.mindhub.wireit.dto.bodyjson.NewCart;
import com.mindhub.wireit.dto.bodyjson.PurchaseRequest;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.ProductOrder;
import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.models.enums.OrderStatus;
import com.mindhub.wireit.repositories.ClientRepository;
import com.mindhub.wireit.repositories.ProductRepository;
import com.mindhub.wireit.repositories.PurchaseOrderRepository;
import com.mindhub.wireit.service.PdfService;
import com.mindhub.wireit.service.ProductOrderService;
import com.mindhub.wireit.service.ProductService;
import com.mindhub.wireit.service.PurchaseOrderService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private HttpServletResponse response;


    @Override
    @Transactional
    public ResponseEntity<String> purchaseProcess(PurchaseRequest purchaseRequest, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
            if (client == null) {
                return new ResponseEntity<>("You need to be logged in to checkout", HttpStatus.FORBIDDEN);
            }

            if (purchaseRequest.getDiscount() < 0) {
                return new ResponseEntity<>("Discount can not be less than 0", HttpStatus.FORBIDDEN);
            }

            PurchaseOrder purchaseOrder = new PurchaseOrder();

            for (NewCart item : purchaseRequest.getItems()) {

                if (item == null) {
                    return new ResponseEntity<>("Please, add products to your cart before checkout", HttpStatus.FORBIDDEN);
                }

                Long productId = item.getProductId();
                int quantity = item.getQuantity();

                Product product = productService.getProductById(productId);

                if (quantity > product.getStock()) {
                    return new ResponseEntity<>("Not enough stock available for " + product.getName(), HttpStatus.FORBIDDEN);
                }
                if (product != null) {
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setProduct(product);
                    productOrder.setQuantity((byte) quantity);
                    purchaseOrder.addProductOrder(productOrder);
                    productOrderService.saveProductOrder(productOrder);

                    product.setStock(product.getStock() - productOrder.getQuantity());
                }
            }

            purchaseOrder.setOrderNumber(OrderNumberGenerator.OrderNumberGenerator());
            purchaseOrder.setShipment_date(LocalDate.now());
            purchaseOrder.setAdditionalComment(purchaseRequest.getComment());
            if (purchaseRequest.getDiscount() == 0) {
                purchaseOrder.setDiscount(1);
            } else {
                purchaseOrder.setDiscount(purchaseRequest.getDiscount());
            }
            purchaseOrder.setOrderStatus(OrderStatus.CREATED);

            List<ProductOrder> productOrders = purchaseOrder.getProductOrders();
            CalculateTotalAmount.calculateTotalPrice(purchaseOrder, productOrders);

            purchaseOrder.setTotalToPay(purchaseOrder.getTotalAmount() * (1 - (purchaseOrder.getDiscount() / 100)));

            client.addOrders(purchaseOrder);

            savePurchaseOrder(purchaseOrder);

        try {
            sendEmailAttachment(authentication, purchaseOrder.getOrderNumber());
            return new ResponseEntity<>("Purchase order Successfully", HttpStatus.CREATED);
        } catch (IOException ex) {
            ex.printStackTrace(); // Imprime la traza de la excepción en la consola (puedes cambiar esto según tus necesidades)
            return new ResponseEntity<>("Error sending email attachment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void sendEmailAttachment(Authentication authentication, String orderNumber) throws IOException {
        Client client = clientRepository.findByEmail(authentication.getName());

        Dotenv dotenv = Dotenv.configure().load();

        String email = dotenv.get("EMAIL");
        Email from = new Email(email);

        String subject = "Wireit - Purchase order";
        Email to = new Email(client.getEmail());
        Content content = new Content("text/plain", "Your order has been successfully created.");
        Mail mail = new Mail(from, subject, to, content);

        String apiKey = dotenv.get("SENDGRID_API_KEY");

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            // Obtener el PDF como byte[]
            ByteArrayOutputStream pdfStream = pdfService.export(response, orderNumber);
            byte[] pdfBytes = pdfStream.toByteArray();
            pdfStream.close();

            // Adjuntar el PDF al correo electrónico
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(pdfBytes));
            attachments.setType("application/pdf");
            attachments.setFilename("order_" + orderNumber + ".pdf");
            attachments.setDisposition("attachment");
            attachments.setContentId("OrderAttachment");

            mail.addAttachments(attachments);

            // Resto de tu código para enviar el correo electrónico
            request.setBody(mail.build());
            Response response = sg.api(request);
        if (response.getStatusCode() == HttpStatus.OK.value()) {
            System.out.println("Email sent successfully");
        } else {
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        }
    }

    @Override
    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);
    }
}
