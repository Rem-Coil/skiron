package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.OperationType;
import com.remcoil.skiron.database.entity.Specification;
import com.remcoil.skiron.database.repository.OperationTypeRepository;
import com.remcoil.skiron.model.operation.OperationTypeBrief;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OperationTypeService {
    private final OperationTypeRepository operationTypeRepository;

    public OperationTypeService(OperationTypeRepository operationTypeRepository) {
        this.operationTypeRepository = operationTypeRepository;
    }

    public List<OperationType> create(List<OperationTypeBrief> operationTypes, Specification specification) {
        return operationTypeRepository.saveAll(operationTypes
                .stream()
                .map(it -> it.toEntity(specification))
                .toList()
        );
    }

    public void updateOperationTypes(Set<Long> oldTypesId, List<OperationType> newTypes) {
        for (OperationType operation : newTypes) {
            if (oldTypesId.contains(operation.getId())) {
                operationTypeRepository.save(operation);
                oldTypesId.remove(operation.getId());
            } else {
                operationTypeRepository.save(operation);
            }
        }
        for (Long id : oldTypesId) {
            deleteById(id);
        }
    }

    public void deleteById(Long id) {
        operationTypeRepository.deleteById(id);
    }
}
