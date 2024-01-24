package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.repositories.ProductRepository;
import com.mindhub.wireit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllProductsDTO() {
        return getAllProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsFiltered(ProductCategory productCategory) {
        return getAllProducts().stream().filter(product -> product.getProductCategory() == productCategory).map(ProductDTO::new).collect(Collectors.toList());
    }


}
