package com.mindhub.wireit.service;

import com.mindhub.wireit.dto.ClientDTO;
import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<ProductDTO> getAllProductsDTO();

    List<ProductDTO> getAllProductsFiltered(ProductCategory productCategory);

    ResponseEntity<String> createProduct(NewProduct newProduct);
}
