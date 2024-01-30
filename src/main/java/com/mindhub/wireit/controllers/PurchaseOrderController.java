package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.bodyjson.NewCart;
import com.mindhub.wireit.dto.bodyjson.PurchaseRequest;
import com.mindhub.wireit.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/checkout")
    public ResponseEntity<String> purchaseProcess(@RequestBody PurchaseRequest purchaseRequest, Authentication authentication){
        ResponseEntity<String> response = purchaseOrderService.purchaseProcess(purchaseRequest,authentication);
        return response;
    }
}
