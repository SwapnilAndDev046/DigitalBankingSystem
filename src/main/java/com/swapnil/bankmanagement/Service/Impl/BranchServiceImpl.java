package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.BranchDto;
import com.swapnil.bankmanagement.Entity.Branch;
import com.swapnil.bankmanagement.Repository.BranchRepository;
import com.swapnil.bankmanagement.Service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = modelMapper.map(branchDto,Branch.class);
        Branch savedBranch = branchRepository.save(branch);
        return modelMapper.map(savedBranch,BranchDto.class);
    }

    @Transactional
    @Override
    public String deleteBranch(Long branchID) {
        Branch branch = branchRepository.findById(branchID)
                .orElseThrow(()->new EntityNotFoundException("Branch Not Found With ID: "+branchID));

        branchRepository.deleteById(branchID);

        return "branch with id "+branchID+" is deleted.";
    }

    @Override
    public List<BranchDto> getAllBranches() {
        return branchRepository
                .findAll()
                .stream()
                .map(n->modelMapper.map(n,BranchDto.class))
                .toList();
    }

    @Transactional
    @Override
    public BranchDto updateBranch(BranchDto branchDto, Long branchID) {
        Branch branch = branchRepository
                .findById(branchID)
                .orElseThrow(()->new EntityNotFoundException("Branch Not Found With ID: "+branchID));

        modelMapper.map(branchDto,branch);

        Branch savedBranch = branchRepository.save(branch);

        return modelMapper.map(savedBranch,BranchDto.class);
    }

    @Transactional
    @Override
    public BranchDto partialUpdateBranch(Map<String, Object> entry, Long branchID) {
        Branch branch = branchRepository
                .findById(branchID)
                .orElseThrow(()->new EntityNotFoundException("Branch Not Found With ID: "+branchID));

        entry.forEach((key,value)->{
            switch (key){
                case "branchName":
                    branch.setBranchName(String.valueOf(value));
                    break;
                case "IFSC_CODE":
                    branch.setIFSC_CODE(String.valueOf(value));
                    break;
                case "city":
                    branch.setCity(String.valueOf(value));
                    break;
                case "state":
                    branch.setState(String.valueOf(value));
                    break;
                default:
                    throw new EntityNotFoundException("Entity Input is Wrong..");
            }
        });
        Branch savedBranch = branchRepository.save(branch);

        return modelMapper.map(savedBranch,BranchDto.class);
    }


}
