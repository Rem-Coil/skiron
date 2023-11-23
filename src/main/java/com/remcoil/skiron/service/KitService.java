package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.Batch;
import com.remcoil.skiron.database.entity.Kit;
import com.remcoil.skiron.database.repository.KitRepository;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitPostRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KitService {
    private final KitRepository kitRepository;
    private final BatchService batchService;

    public List<KitFull> getAll() {
        return kitRepository.findAll().stream()
                .map(KitFull::fromEntity)
                .toList();
    }

    @Transactional
    public KitFull create(KitPostRequest kitRequest) {
        KitFull kit = KitFull.fromEntity(kitRepository.save(kitRequest.toEntity()));
        batchService.create(kit, 1);
        return kit;
    }

    @Transactional
    public void update(KitFull newKit) {
        Optional<Kit> kitOptional = kitRepository.findById(newKit.id());
        if (kitOptional.isEmpty()) {
            throw new EntryDoesNotExistException("Not Found");
        }
        KitFull oldKit = KitFull.fromEntity(kitOptional.get());
        List<Batch> batches = batchService.getByKitId(newKit.id());

        batchService.resizeBatches(oldKit, newKit, batches);
        batchService.updateBatchesQuantity(oldKit, newKit, batches);
        kitRepository.save(newKit.toEntity());
    }

    public void deleteById(long id) {
        kitRepository.deleteById(id);
    }
}
