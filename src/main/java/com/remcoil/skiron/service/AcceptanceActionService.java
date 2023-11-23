package com.remcoil.skiron.service;

import com.remcoil.skiron.database.repository.AcceptanceActionRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionBrief;
import com.remcoil.skiron.model.action.acceptance.AcceptanceActionPostRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AcceptanceActionService {
    private final AcceptanceActionRepository acceptanceActionRepository;

    public List<AcceptanceActionBrief> getAll() {
        return acceptanceActionRepository.findAll().stream()
                .map(AcceptanceActionBrief::fromEntity)
                .toList();
    }

    public List<AcceptanceActionBrief> create(AcceptanceActionPostRequest actionRequest) {
        return AcceptanceActionBrief.fromEntities(
                acceptanceActionRepository.saveAll(actionRequest.toEntity())
        );
    }

    public AcceptanceActionBrief update(AcceptanceActionBrief actionBrief) {
        acceptanceActionRepository.findById(actionBrief.id())
                .orElseThrow(() -> new EntryDoesNotExistException("Not Found"));
        return AcceptanceActionBrief.fromEntity(acceptanceActionRepository.save(actionBrief.toEntity()));
    }

    public void deleteById(Long id) {
        acceptanceActionRepository.deleteById(id);
    }

    public void deleteByProducts(List<Long> productsId) {
        acceptanceActionRepository.deleteByProductsId(productsId);
    }
}
