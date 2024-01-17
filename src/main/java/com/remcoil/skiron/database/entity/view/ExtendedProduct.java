package com.remcoil.skiron.database.entity.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Data
@Entity
@Immutable
@Table(name = "extended_products")
public class ExtendedProduct {
    @Id
    Long id;

    Integer productNumber;
    Boolean active;
    Boolean locked;
    Long batchId;
    Integer batchNumber;
    Long kitId;
}
