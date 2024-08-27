package com.xavier.dscommerce.services;

import com.xavier.dscommerce.dto.ProductMinDTO;
import com.xavier.dscommerce.repositories.ProductRepository;
import com.xavier.dscommerce.dto.ProductDTO;
import com.xavier.dscommerce.entities.Product;
import com.xavier.dscommerce.services.exceptions.DataBaseException;
import com.xavier.dscommerce.services.exceptions.ResouceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ResouceNotFoundException("Recurso não encontrado"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable){
        Page<Product> product = productRepository.searchByName(name, pageable);
        return product.map(ProductMinDTO::new);
    }
    @Transactional()
    public ProductDTO insert(ProductDTO productDTO){
        Product entity = new Product();
        copyDtoToEntity(productDTO, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional()
    public ProductDTO update(Long id, ProductDTO productDTO){
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(productDTO, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResouceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!productRepository.existsById(id)){
            throw new ResouceNotFoundException("Recurso não encontrado");
        }
        try{
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Falha de integridade referencial");
        }
    }
    private void copyDtoToEntity(ProductDTO productDTO, Product entity) {
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setPrice(productDTO.getPrice());
        entity.setImgUrl(productDTO.getImgUrl());
    }


}
