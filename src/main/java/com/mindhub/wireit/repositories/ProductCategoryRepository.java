package com.mindhub.wireit.repositories;

import com.mindhub.wireit.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByCategoryIgnoreCase(String category);

}
