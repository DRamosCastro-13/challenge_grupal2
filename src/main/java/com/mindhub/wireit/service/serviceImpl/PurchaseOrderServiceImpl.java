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
import com.mindhub.wireit.repositories.ProductOrderRepository;
import com.mindhub.wireit.repositories.ProductRepository;
import com.mindhub.wireit.repositories.PurchaseOrderRepository;
import com.mindhub.wireit.service.ClientService;
import com.mindhub.wireit.service.ProductOrderService;
import com.mindhub.wireit.service.ProductService;
import com.mindhub.wireit.service.PurchaseOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    @Transactional
    public ResponseEntity<String> purchaseProcess(PurchaseRequest purchaseRequest, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("You need to be logged in to checkout", HttpStatus.FORBIDDEN);
        }

        if(purchaseRequest.getDiscount() < 0){
            return new ResponseEntity<>("Discount can not be less than 0", HttpStatus.FORBIDDEN);
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        for(NewCart item : purchaseRequest.getItems()){

            if(item == null){
                return new ResponseEntity<>("Please, add products to your cart before checkout", HttpStatus.FORBIDDEN);
            }

            Long productId = item.getProductId();
            int quantity = item.getQuantity();

            Product product = productService.getProductById(productId);

            if (quantity > product.getStock()) {
                return new ResponseEntity<>("Not enough stock available for " + product.getName(), HttpStatus.FORBIDDEN);
            }
            if(product != null){
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
        if(purchaseRequest.getDiscount() == 0){
            purchaseOrder.setDiscount(1);
        } else {
            purchaseOrder.setDiscount(purchaseRequest.getDiscount());
        }
        purchaseOrder.setOrderStatus(OrderStatus.CREATED);

        List<ProductOrder> productOrders = purchaseOrder.getProductOrders();
        CalculateTotalAmount.calculateTotalPrice(purchaseOrder, productOrders);

        client.addOrders(purchaseOrder);

        savePurchaseOrder(purchaseOrder);

        return new ResponseEntity<>("Purchase order Successfully", HttpStatus.CREATED);
    }

    @Override
    public void savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderRepository.save(purchaseOrder);
    }


}
