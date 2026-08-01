package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.BranchDto;
import com.swapnil.bankmanagement.Service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    BranchDto createBranch(@RequestBody BranchDto branchDto) {
        return branchService.createBranch(branchDto);
    }

    @DeleteMapping("/{id}")
    String deleteBranch(@PathVariable Long id){
        return branchService.deleteBranch(id);
    }

    @GetMapping
    List<BranchDto> getAllBranches(){
        return branchService.getAllBranches();
    }

    @PutMapping("/{id}")
    BranchDto updateBranch(@RequestBody BranchDto branchDto, @PathVariable Long id){
        return branchService.updateBranch(branchDto,id);
    }

    @PatchMapping("/{id}")
    BranchDto partialUpdateBranch(@RequestBody Map<String,Object> branchDto, @PathVariable Long id){
        return branchService.partialUpdateBranch(branchDto,id);
    }
}
