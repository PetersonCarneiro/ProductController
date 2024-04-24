package com.br.ProductController.controller;

import com.br.ProductController.domain.product.Product;
import com.br.ProductController.domain.product.ProductRequestDTO;
import com.br.ProductController.domain.product.ProductResponseDTO;
import com.br.ProductController.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity postProduct(@RequestBody ProductRequestDTO body){
        Product newProduct = new Product(body);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllProduct(){
        List<ProductResponseDTO> responseDTOList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok(responseDTOList);
    }

    
}
