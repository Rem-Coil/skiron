package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.ControlAction;
import com.remcoil.skiron.database.repository.ControlActionRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.exception.InactiveProductException;
import com.remcoil.skiron.exception.LockedProductException;
import com.remcoil.skiron.model.action.control.ControlActionBrief;
import com.remcoil.skiron.model.action.control.ControlActionPostRequest;
import com.remcoil.skiron.model.product.ProductFull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ControlActionService {
    private final ControlActionRepository controlActionRepository;
    private final ProductService productService;

    public List<ControlActionBrief> getAll() {
        return controlActionRepository.findAll().stream()
                .map(ControlActionBrief::fromEntity)
                .toList();
    }

    public List<ControlAction> getByProductId(long id) {
        return controlActionRepository.findAllByProductId(id);
    }

    @Transactional
    public List<ControlActionBrief> create(ControlActionPostRequest actionRequest) {
        List<ProductFull> products = validateProducts(actionRequest);
        if (actionRequest.needRepair()) {
            lockProducts(products);
        }

        return ControlActionBrief.fromEntities(
                controlActionRepository.saveAll(actionRequest.toEntities())
        );
    }

    private void lockProducts(List<ProductFull> products) {
        for (ProductFull product : products) {
            productService.setLockStatusById(product.id(), true);
        }
    }

    private List<ProductFull> validateProducts(ControlActionPostRequest actionRequest) {
        List<ProductFull> products = new ArrayList<>();
        for (long id : actionRequest.productsId()) {
            ProductFull product = productService.getById(id);
            if (!product.active()) {
                throw new InactiveProductException("Product is inactive");
            }
            if (product.locked()) {
                throw new LockedProductException("Product is locked");
            }
            products.add(product);
        }
        return products;
    }

    @Transactional
    public ControlActionBrief update(ControlActionBrief actionBrief) {
        controlActionRepository.findById(actionBrief.id())
                .orElseThrow(() -> new EntryDoesNotExistException("Not Found"));
        return ControlActionBrief.fromEntity(controlActionRepository.save(actionBrief.toEntity()));
    }

    @Transactional
    public void deleteById(long id) {
        controlActionRepository.deleteById(id);
    }

    public void deleteByProductsId(List<Long> productsId) {
        controlActionRepository.deleteByProductsId(productsId);
    }
}
