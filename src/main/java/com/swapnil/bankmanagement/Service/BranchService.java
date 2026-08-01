package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.BranchDto;

import java.util.List;
import java.util.Map;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDto);
    String deleteBranch(Long branchID);
    List<BranchDto> getAllBranches();
    BranchDto updateBranch(BranchDto branchDto,Long branchID);
    BranchDto partialUpdateBranch(Map<String,Object> entry, Long branchID);
}
