package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.OperationType;
import com.remcoil.skiron.database.entity.Specification;
import com.remcoil.skiron.database.repository.OperationTypeRepository;
import com.remcoil.skiron.model.operation.OperationTypeBrief;
import com.remcoil.skiron.model.operation.OperationTypeFull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OperationTypeService {
    private final OperationTypeRepository operationTypeRepository;

    protected List<OperationTypeFull> getBySpecificationId(Long id) {
        return operationTypeRepository.findAllBySpecificationId(id).stream()
                .map(OperationTypeFull::fromEntity)
                .toList();
    }

    protected List<OperationType> create(List<OperationTypeBrief> operationTypes, Specification specification) {
        return operationTypeRepository.saveAll(operationTypes
                .stream()
                .map(it -> it.toEntity(specification))
                .toList()
        );
    }

    protected void updateOperationTypes(Set<Long> oldTypesId, List<OperationType> newTypes) {
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
