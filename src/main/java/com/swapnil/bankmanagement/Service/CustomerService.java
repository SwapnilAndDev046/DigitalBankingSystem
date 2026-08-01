package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    String deleteCustomer(Long customerID);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(CustomerDto customerDto,Long customerID);
    CustomerDto patchCustomer(Map<String,Object> entry, Long customerID);
    Page<CustomerDto> getCustomersWithLimit(int page,int limit);
}
