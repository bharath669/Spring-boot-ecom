package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long categoryId;
    @NotBlank
    @Size(min=5,message = "Category Name must contain at-least 5 Characters")
//    @Size(min = 5)
    private String categoryName;

    @OneToMany(mappedBy ="category",cascade = CascadeType.ALL)
    private List<Product> products;
}
