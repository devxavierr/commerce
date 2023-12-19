package com.xavier.dscommerce.services;

import com.xavier.dscommerce.Repositories.ProductRepository;
import com.xavier.dscommerce.dto.ProductDTO;
import com.xavier.dscommerce.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> product = productRepository.findAll(pageable);
        return product.map(x-> new ProductDTO(x));
    }
    @Transactional()
    public ProductDTO insert(ProductDTO productDTO){
        Product entity = new Product();
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());

        entity = productRepository.save(entity);

        return new ProductDTO(entity);
    }


}
