package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.OperationType;
import com.remcoil.skiron.database.entity.Specification;
import com.remcoil.skiron.database.repository.SpecificationRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.model.specification.SpecificationBrief;
import com.remcoil.skiron.model.specification.SpecificationFull;
import com.remcoil.skiron.model.specification.SpecificationRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecificationService {
    private final SpecificationRepository specificationRepository;
    private final OperationTypeService operationTypeService;

    public SpecificationService(SpecificationRepository specificationRepository, OperationTypeService operationTypeService) {
        this.specificationRepository = specificationRepository;
        this.operationTypeService = operationTypeService;
    }

    public List<SpecificationBrief> getAll() {
        return specificationRepository.findAll()
                .stream()
                .map(SpecificationBrief::fromEntity)
                .collect(Collectors.toList());
    }

    public SpecificationFull getById(Long id) {
        Optional<Specification> specification = specificationRepository.findByIdAndFetch(id);
        if (specification.isPresent()) {
            return SpecificationFull.fromEntity(specification.get());
        } else {
            throw new EntryDoesNotExistException("Not Found");
        }
    }


    @Transactional
    public SpecificationFull create(SpecificationRequest specificationRequest) {
        Specification specification = specificationRepository.save(specificationRequest.toCreateEntity());
        List<OperationType> operationTypes = operationTypeService.create(specificationRequest.operationTypes(), specification);

        specification.setOperationTypes(operationTypes);

        return SpecificationFull.fromEntity(specification);
    }

    @Transactional
    public void update(SpecificationFull specificationFull) {
        Optional<Specification> optionalSpecification = specificationRepository.findByIdAndFetch(specificationFull.id());

        if (optionalSpecification.isEmpty()) {
            throw new EntryDoesNotExistException("Not Found");
        }

        operationTypeService.updateOperationTypes(
                optionalSpecification.get().getOperationTypes().stream()
                        .map(OperationType::getId)
                        .collect(Collectors.toSet()),
                specificationFull.toEntityWithOperations().getOperationTypes()
        );
        specificationRepository.save(specificationFull.toEntity());
    }

    public void deleteById(Long id) {
        specificationRepository.deleteById(id);
    }
}
