package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.database.repository.KitRepository;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KitService {
    private final KitRepository kitRepository;
    private final BatchService batchService;

    public KitService(KitRepository kitRepository, BatchService batchService) {
        this.kitRepository = kitRepository;
        this.batchService = batchService;
    }

    @Transactional
    public KitFull create(KitRequest kitRequest) {
        Kit kit = kitRepository.save(kitRequest.toEntity());
        batchService.create(kit);
        return KitFull.fromEntity(kit);
    }

    public List<KitFull> getAll() {
        return kitRepository.findAll().stream()
                .map(KitFull::fromEntity)
                .collect(Collectors.toList());
    }
}
