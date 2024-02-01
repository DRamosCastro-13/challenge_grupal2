package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
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
    public List<ProductDTO> getAllProductsFiltered(@RequestParam String category){
        return productService.getAllProductsFiltered(category);
    }

    @PostMapping("/products/new")
    public ResponseEntity<String> createProduct(@RequestBody NewProduct newProduct, Authentication authentication){
        ResponseEntity<String> response = productService.createProduct(newProduct,authentication);
        return response;
    }

    @PatchMapping("/products/update")
    public ResponseEntity<String> updateProduct(@RequestParam Long id, @RequestBody NewProduct newProduct, Authentication authentication){
        ResponseEntity<String> response = productService.updateProduct(id, newProduct, authentication);
        return response;
    }

    @PostMapping ("/products/newCategory")
    public ResponseEntity<String> newCategory(@RequestParam String category, Authentication authentication){
        ResponseEntity<String> response = productService.newCategory(category, authentication);
        return  response;
    }

}
