package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min = 5,message = "Product Name contain at-least 5 character")
    private String productName;
    private String image;
    private String Description;
    private Integer quantity;
    private double price;//100
    private double discount;//25
    private double specialPrice;//75

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
