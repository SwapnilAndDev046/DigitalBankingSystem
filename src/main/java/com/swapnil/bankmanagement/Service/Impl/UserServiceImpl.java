package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Dto.SignupRequestDto;
import com.swapnil.bankmanagement.Dto.SignupResponseDto;
import com.swapnil.bankmanagement.Entity.AppUser;

import com.swapnil.bankmanagement.Exception.CustomerNotFound;
import com.swapnil.bankmanagement.Exception.UserAlreadyExists;
import com.swapnil.bankmanagement.Repository.UserRepository;
import com.swapnil.bankmanagement.Security.CurrentUserService;
import com.swapnil.bankmanagement.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

//    @Transactional
//    @Override
//    public CustomerDto createCustomer(CustomerDto customerDto) {
//        AppUser customer = modelMapper.map(customerDto,Customer.class);
//        AppUser savedCustomer = userRepository.save(customer);
//        return modelMapper.map(savedCustomer,CustomerDto.class);
//    }

    @Transactional
    @Override
    public String deleteCustomer(Long customerID) {
        AppUser customer = userRepository
                .findById(customerID)
                .orElseThrow(()->new EntityNotFoundException("Customer Not Found With Id:"+customerID));

        userRepository.deleteById(customerID);
        return "Customer Deleted With ID:"+customerID;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customers = userRepository
                .findAll()
                .stream()
                .map(n->modelMapper.map(n,CustomerDto.class))
                .toList();

        return customers;
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        AppUser customer = userRepository
                .findByEmail(currentUserService.getCurrentUser().getEmail());

        if (customer == null)
            throw new CustomerNotFound("Customer not found");


        //Converting DTO to Existing Entity
        modelMapper.map(customerDto,customer);

        //Without .save() it won't get update in DB
        AppUser savedCustomer = userRepository
                .save(customer);
        return modelMapper.map(savedCustomer,CustomerDto.class);
    }

    @Transactional
    @Override
    public CustomerDto patchCustomer(Map<String, Object> entry) {
        AppUser customer = userRepository
                .findByEmail(currentUserService.getCurrentUser().getEmail());

        if (customer == null)
            throw new CustomerNotFound("User Not Found");

        entry.forEach((key,value)->{
                    switch (key){
                        case "name":
                            customer.setName(String.valueOf(value));
                            break;
                        case "email":
                            customer.setEmail(String.valueOf(value));
                            break;
                        case "phoneNumber":
                            customer.setPhoneNumber(String.valueOf(value));
                            break;
                        default:
                            throw new EntityNotFoundException("Entity Input is Wrong..");
                    }
                });

        AppUser savedCustomer = userRepository
                .save(customer);

        return modelMapper.map(savedCustomer,CustomerDto.class);
        
    }

    @Override
    public Page<CustomerDto> getCustomersWithLimit(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<AppUser> customer = userRepository.findCustomerWithLimit(pageable);

        return customer.map(n->modelMapper.map(n,CustomerDto.class));
    }

}
