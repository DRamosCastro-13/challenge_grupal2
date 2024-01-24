package com.mindhub.wireit.controllers;

import com.mindhub.wireit.repositories.dto.ProductDTO;
import com.mindhub.wireit.repositories.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    //controlador de: productOrder, product, supplier

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProductsDTO();
    }

    @GetMapping("/products/filtered")
    public List<ProductDTO> getAllProductsFiltered(@RequestParam ProductCategory productCategory){
        return productService.getAllProductsFiltered(productCategory);
    }

    @PostMapping("/products/new")
    public ResponseEntity<String> createProduct(@RequestBody NewProduct newProduct, Authentication authentication){
        ResponseEntity<String> response = productService.createProduct(newProduct,authentication);
        return response;
    }

}
