package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Entity.Customer;
import com.swapnil.bankmanagement.Repository.CustomerRepository;
import com.swapnil.bankmanagement.Service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto,Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer,CustomerDto.class);
    }

    @Transactional
    @Override
    public String deleteCustomer(Long customerID) {
        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(()->new EntityNotFoundException("Customer Not Found With Id:"+customerID));

        customerRepository.deleteById(customerID);
        return "Customer Deleted With ID:"+customerID;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customers = customerRepository
                .findAll()
                .stream()
                .map(n->modelMapper.map(n,CustomerDto.class))
                .toList();

        return customers;
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerID) {
        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(()->new EntityNotFoundException("Customer not Found With ID: "+customerID));

        //Converting DTO to Existing Entity
        modelMapper.map(customerDto,customer);

        //Without .save() it won't get update in DB
        Customer savedCustomer = customerRepository
                .save(customer);
        return modelMapper.map(savedCustomer,CustomerDto.class);
    }

    @Transactional
    @Override
    public CustomerDto patchCustomer(Map<String, Object> entry, Long customerID) {
        Customer customer = customerRepository
                .findById(customerID)
                .orElseThrow(()->new EntityNotFoundException("Customer Not Found With ID: "+customerID));

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

        Customer savedCustomer = customerRepository
                .save(customer);

        return modelMapper.map(savedCustomer,CustomerDto.class);
        
    }

    @Override
    public Page<CustomerDto> getCustomersWithLimit(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<Customer> customer = customerRepository.findCustomerWithLimit(pageable);

        return customer.map(n->modelMapper.map(n,CustomerDto.class));
    }
}
