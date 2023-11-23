package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Action;
import com.remcoil.skiron.database.entity.ControlAction;
import com.remcoil.skiron.database.repository.ActionRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.exception.InactiveProductException;
import com.remcoil.skiron.exception.LockedProductException;
import com.remcoil.skiron.exception.UnlockedProductException;
import com.remcoil.skiron.model.action.ActionBrief;
import com.remcoil.skiron.model.action.ActionPostRequest;
import com.remcoil.skiron.model.product.ProductFull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ActionService {
    private final ControlActionService controlActionService;
    private final ActionRepository actionRepository;
    private final ProductService productService;

    public List<ActionBrief> getAll() {
        return actionRepository.findAll().stream()
                .map(ActionBrief::fromEntity)
                .toList();
    }

    @Transactional
    public List<ActionBrief> create(ActionPostRequest actionRequest) {
        List<ProductFull> products = validateProducts(actionRequest);
        List<Action> actions = actionRequest.repair() ? validateRepairActions(actionRequest, products) : actionRequest.toEntities();

        return ActionBrief.fromEntities(
                actionRepository.saveAll(actions)
        );
    }

    private List<Action> validateRepairActions(ActionPostRequest actionRequest, List<ProductFull> products) {
        for (ProductFull product : products) {
            ControlAction lastControlAction = controlActionService.getByProductId(product.id()).stream()
                    .filter(ControlAction::getNeedRepair)
                    .max(Comparator.comparing(ControlAction::getDoneTime))
                    .orElseThrow();
            if (lastControlAction.getOperationTypeId().equals(actionRequest.operationTypeId())) {
                productService.setLockStatusById(product.id(), false);
            } else {
                throw new LockedProductException("Locked by another control action");
            }
        }
        return actionRequest.toEntities();
    }

    private List<ProductFull> validateProducts(ActionPostRequest actionRequest) {
        List<ProductFull> products = new ArrayList<>();
        for (long id : actionRequest.productsId()) {
            ProductFull product = productService.getById(id);
            if (!product.active()) {
                throw new InactiveProductException("Product is inactive");
            }
            if (product.locked() && !actionRequest.repair()) {
                throw new LockedProductException("Product is locked");
            }
            if (actionRequest.repair() && !product.locked()) {
                throw new UnlockedProductException("Product is unlocked");
            }
            products.add(product);
        }
        return products;
    }

    public ActionBrief update(ActionBrief actionBrief) {
        actionRepository.findById(actionBrief.id())
                .orElseThrow(() -> new EntryDoesNotExistException("Not Found"));
        return ActionBrief.fromEntity(actionRepository.save(actionBrief.toEntity()));
    }

    public void deleteById(long id) {
        actionRepository.deleteById(id);
    }

    public void deleteByProductsId(List<Long> productsId) {
        actionRepository.deleteByProductsId(productsId);
    }
}
