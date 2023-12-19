package com.xavier.dscommerce.services;

import com.xavier.dscommerce.Repositories.ProductRepository;
import com.xavier.dscommerce.dto.ProductDTO;
import com.xavier.dscommerce.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).get();
        return new ProductDTO(product);
    }

}
