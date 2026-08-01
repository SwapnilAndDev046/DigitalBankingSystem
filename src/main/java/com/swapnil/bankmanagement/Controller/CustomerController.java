package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<CustomerDto> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id){
        return customerService.updateCustomer(customerDto,id);
    }

    @PatchMapping("/{id}")
    public CustomerDto patchCustomer(@RequestBody Map<String,Object> entry, @PathVariable Long id){
        return customerService.patchCustomer(entry,id);
    }

    //http://localhost:8080/api/v1/customers/pagination?page=0&size=5 pagination
    @GetMapping("/pagination")
    Page<CustomerDto> getCustomersWithLimit(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
            return  customerService.getCustomersWithLimit(page, size);
    }

}
