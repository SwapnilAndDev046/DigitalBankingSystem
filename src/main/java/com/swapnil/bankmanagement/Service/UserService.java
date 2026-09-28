package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Dto.SignupRequestDto;
import com.swapnil.bankmanagement.Dto.SignupResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.RequestEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
//    CustomerDto createCustomer(CustomerDto customerDto);
    String deleteCustomer(Long customerID);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(CustomerDto customerDto);
    CustomerDto patchCustomer(Map<String,Object> entry);
    Page<CustomerDto> getCustomersWithLimit(int page,int limit);

}
