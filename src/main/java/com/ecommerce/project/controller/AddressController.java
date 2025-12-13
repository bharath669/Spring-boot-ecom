package com.ecommerce.project.controller;

import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user=authUtil.loggedInUser();
        AddressDTO savedaddressDTO=addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(savedaddressDTO, HttpStatus.CREATED);
    }
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddress(){
        List<AddressDTO> addressDTOList=addressService.getAddress();
        return new ResponseEntity<>(addressDTOList  ,HttpStatus.OK);
    }
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO=addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getAddressByUser(){
        User user= authUtil.loggedInUser();
        List<AddressDTO> addressDTO=addressService.getAddressByUser(user);
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updatedAddress(@PathVariable Long addressId,
                                                     @RequestBody AddressDTO addressDTO){
        AddressDTO updatesAddressDTO=addressService.updatedAddress(addressId,addressDTO);
        return new ResponseEntity<>(updatesAddressDTO,HttpStatus.OK);
    }
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        String status=addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
