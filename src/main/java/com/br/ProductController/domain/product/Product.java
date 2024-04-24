package com.br.ProductController.domain.product;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer price;

    public Product(ProductRequestDTO productRequestDTO){
        this.name = productRequestDTO.name();
        this.price = productRequestDTO.price();
    }
}
