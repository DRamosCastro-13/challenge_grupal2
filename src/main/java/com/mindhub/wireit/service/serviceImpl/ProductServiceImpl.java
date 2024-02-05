package com.mindhub.wireit.service.serviceImpl;

import com.mindhub.wireit.dto.ProductDTO;
import com.mindhub.wireit.dto.bodyjson.NewProduct;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.Product;
import com.mindhub.wireit.models.ProductCategory;
import com.mindhub.wireit.models.enums.Role;
import com.mindhub.wireit.repositories.ClientRepository;
import com.mindhub.wireit.repositories.ProductCategoryRepository;
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

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllProductsDTO() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsFiltered(String category) {
        return getAllProducts().stream().filter(product -> product.getCategory().getCategory().equals(category)).map(ProductDTO::new).collect(Collectors.toList());
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

        if (newProduct.getCategory() == null || newProduct.getCategory().isBlank()) {
            return new ResponseEntity<>("Invalid or missing product category", HttpStatus.FORBIDDEN);
        }

        ProductCategory category = productCategoryRepository.findByCategoryIgnoreCase(newProduct.getCategory());

        if (category == null) {
            return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);
        }

        if(newProduct.getStock()<= 0){
            return new ResponseEntity<>("Stock can not be 0 or less.", HttpStatus.FORBIDDEN);
        }

        Product product = new Product(newProduct.getName(), newProduct.getBrand(), newProduct.getImage_url(), newProduct.getDescription(), category, newProduct.getPrice(), newProduct.getDiscount(), newProduct.getStock());
        productRepository.save(product);

        return new ResponseEntity<>("Product created successfully",HttpStatus.CREATED);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<String> updateProduct(Long id, NewProduct newProduct, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("Invalid User", HttpStatus.FORBIDDEN);
        }

        if(client.getRole() != Role.ADMIN){
            return new ResponseEntity<>("You are not admin.",HttpStatus.FORBIDDEN);
        }

        Product product = productRepository.findById(id).orElse(null);

        if(product == null){
            return new ResponseEntity<>("Can not find selected product.",HttpStatus.FORBIDDEN);
        }

        ProductCategory category = productCategoryRepository.findByCategoryIgnoreCase(newProduct.getCategory());

        if (category == null) {
            return new ResponseEntity<>("Category does not exist", HttpStatus.BAD_REQUEST);
        }

        product.setPrice(newProduct.getPrice());
        product.setDiscount(newProduct.getDiscount());
        product.setName(newProduct.getName());
        product.setBrand(newProduct.getBrand());
        product.setImage_url(newProduct.getImage_url());
        product.setDescription(newProduct.getDescription());
        product.setCategory(category);
        product.setStock(newProduct.getStock());

        productRepository.save(product);

        return new ResponseEntity<>("Product updated",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> newCategory(String category, Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("Invalid User", HttpStatus.FORBIDDEN);
        }

        if(client.getRole() != Role.ADMIN){
            return new ResponseEntity<>("You are not admin.",HttpStatus.FORBIDDEN);
        }

        if(category == null){
            return new ResponseEntity<>("Category can not be null", HttpStatus.FORBIDDEN);
        }

        if(category.isBlank()){
            return  new ResponseEntity<>("Category can not be blank", HttpStatus.FORBIDDEN);
        }

        category = category.toUpperCase();

        ProductCategory productCategory = productCategoryRepository.findByCategoryIgnoreCase(category);

        if(productCategory != null){
            return new ResponseEntity<>("Category already exists", HttpStatus.BAD_REQUEST);
        }

        ProductCategory newCategory = new ProductCategory(category);

        productCategoryRepository.save(newCategory);

        return new ResponseEntity<>("Category created Successfully",HttpStatus.CREATED);
    }

}
