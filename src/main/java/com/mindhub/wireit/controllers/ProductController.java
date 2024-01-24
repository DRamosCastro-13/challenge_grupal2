package com.mindhub.wireit.controllers;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
