package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.bodyjson.NewCart;
import com.mindhub.wireit.dto.bodyjson.PurchaseRequest;
import com.mindhub.wireit.models.PurchaseOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface PurchaseOrderService {
    ResponseEntity<String> purchaseProcess(PurchaseRequest purchaseRequest, Authentication authentication) throws IOException;

    void savePurchaseOrder(PurchaseOrder purchaseOrder);
}
