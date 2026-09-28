package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
//    public CustomerDto createCustomer(@RequestBody @Valid CustomerDto customerDto){
//        return userService.createCustomer(customerDto);
//    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id){
        return userService.deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<CustomerDto> getAllCustomers(){
        return userService.getAllCustomers();
    }

    @PutMapping("edits/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id){
        return userService.updateCustomer(customerDto,id);
    }

    @PatchMapping("edit/{id}")
    public CustomerDto patchCustomer(@RequestBody Map<String,Object> entry, @PathVariable Long id){
        return userService.patchCustomer(entry,id);
    }

    //http://localhost:8080/api/v1/customers/pagination?page=0&size=5 pagination
    @GetMapping("/pagination")
    Page<CustomerDto> getCustomersWithLimit(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
            return  userService.getCustomersWithLimit(page, size);
    }

}
