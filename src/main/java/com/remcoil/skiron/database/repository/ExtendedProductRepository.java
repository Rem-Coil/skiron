package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.view.ExtendedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendedProductRepository extends JpaRepository<ExtendedProduct, Long> {
    List<ExtendedProduct> findAllByKitId(Long id);

    List<ExtendedProduct> findAllByBatchId(Long id);
}
