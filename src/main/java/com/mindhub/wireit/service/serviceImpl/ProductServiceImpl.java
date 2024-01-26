package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.models.enums.Role;
import com.mindhub.wireit.repositories.ClientRepository;
import com.mindhub.wireit.repositories.ProductRepository;
import com.mindhub.wireit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    @Override
    public ResponseEntity<String> createProduct(NewProduct newProduct, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("Invalid User", HttpStatus.FORBIDDEN);
        }

        if(client.getRole() == Role.CLIENT){
            return new ResponseEntity<>("Only Admins allowed",HttpStatus.FORBIDDEN);
        }

        if(newProduct.getName().isBlank()){
            return new ResponseEntity<>("Name can not be blank.", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getBrand().isBlank()){
            return new ResponseEntity<>("Brand can not be blank.", HttpStatus.FORBIDDEN);
        }

        if(newProduct.getImage_url().isBlank()){
            return new ResponseEntity<>("Image URL can not be blank.", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getDescription().isBlank()){
            return new ResponseEntity<>("Description can not be blank.", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getDiscount() < 0){
            return new ResponseEntity<>("Discount can not be less than 0.", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getPrice() <= 0){
            return new ResponseEntity<>("Price can not be 0 or less.", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getProductCategory() == null || !EnumSet.allOf(ProductCategory.class).contains(newProduct.getProductCategory())){
            return new ResponseEntity<>("Invalid or missing product category", HttpStatus.FORBIDDEN);
        }
        if(newProduct.getStock()<= 0){
            return new ResponseEntity<>("Stock can not be 0 or less.", HttpStatus.FORBIDDEN);
        }

        Product product = new Product(newProduct.getName(), newProduct.getBrand(), newProduct.getImage_url(), newProduct.getDescription(), newProduct.getProductCategory(), newProduct.getPrice(), newProduct.getDiscount(), newProduct.getStock());
        productRepository.save(product);

        return new ResponseEntity<>("Product create successful",HttpStatus.CREATED);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
