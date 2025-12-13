package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 3,message = "country name must be atleast 3 Characters ")
    private String country;

    @NotBlank
    @Size(min = 4,message = "city name must be atleast 4 Characters ")
    private String city;

    @NotBlank
    @Size(min = 5,message = "street name must be atleast 5 Characters ")
    private String street;

    @NotBlank
    @Size(min = 5,message = "pinCode must be atleast 5 Characters ")
    private String pin_code;

    @NotBlank
    @Size(min = 5,message = "building name must be atleast 5 Characters ")
    private String buildingName;

    @NotBlank
    @Size(min = 4,message = "state name must be atleast 4 Characters ")
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pin_code = pincode;
    }
}
