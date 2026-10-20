package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.BranchDto;
import com.swapnil.bankmanagement.Service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) {
        return ResponseEntity.ok(branchService.createBranch(branchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id){
        return ResponseEntity.ok(branchService.deleteBranch(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BranchDto>> getAllBranches(){
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@RequestBody BranchDto branchDto, @PathVariable Long id){
        return ResponseEntity.ok(branchService.updateBranch(branchDto,id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BranchDto> partialUpdateBranch(@RequestBody Map<String,Object> branchDto, @PathVariable Long id){
        return ResponseEntity.ok(branchService.partialUpdateBranch(branchDto,id));
    }
}
