package com.ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long ProductId;
    private String ProductName;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
