package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address=modelMapper.map(addressDTO, Address.class);
        List<Address> addressList=user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress=addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddress() {
        List<Address> addresses=addressRepository.findAll();
        return addresses.stream()
                .map(address->modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address=addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));
        AddressDTO addressDTO=modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    @Override
    public List<AddressDTO> getAddressByUser(User user) {
        List<Address> addresses=user.getAddresses();
        return addresses.stream()
                .map(address->modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updatedAddress(Long addressId, AddressDTO addressDTO) {
        Address address=addressRepository.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address","addressId",addressId));
        address.setCountry(addressDTO.getCountry());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setPin_code(addressDTO.getPin_code());
        address.setBuildingName(addressDTO.getBuildingName());
        address.setState(addressDTO.getState());

        Address updatedAddress=addressRepository.save(address);

        User user=address.getUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);
        return modelMapper.map(updatedAddress,AddressDTO.class);
    }
}
