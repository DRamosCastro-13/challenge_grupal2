package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<ProductDTO> getAllProductsDTO();

    List<ProductDTO> getAllProductsFiltered(String category);

    ResponseEntity<String> createProduct(NewProduct newProduct, Authentication authentication);

    ResponseEntity<ProductDTO> getSingleProductById(Long id);

    Product getProductById(Long id);

    ResponseEntity<String> updateProduct(Long id, NewProduct newProduct, Authentication authentication);

    ResponseEntity<String> newCategory(String category, Authentication authentication);

}
