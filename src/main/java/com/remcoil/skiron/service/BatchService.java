package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.*;
import com.remcoil.skiron.database.entity.view.ExtendedAcceptanceAction;
import com.remcoil.skiron.database.entity.view.ExtendedAction;
import com.remcoil.skiron.database.entity.view.ExtendedControlAction;
import com.remcoil.skiron.database.entity.view.ExtendedProduct;
import com.remcoil.skiron.database.repository.BatchRepository;
import com.remcoil.skiron.model.batch.BatchFull;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.product.ProductFull;
import com.remcoil.skiron.model.progress.BatchProgress;
import com.remcoil.skiron.model.progress.BatchStat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.remcoil.skiron.model.progress.BatchStat.*;

@Service
@AllArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProductService productService;
    private final ActionService actionService;
    private final ControlActionService controlActionService;
    private final AcceptanceActionService acceptanceActionService;

    public List<BatchFull> getAll() {
        return batchRepository.findAll().stream()
                .map(BatchFull::fromEntity)
                .toList();
    }

    public List<Batch> getByKitId(Long id) {
        return batchRepository.findByKitId(id);
    }

    public void create(KitFull kit, int startNumber) {
        List<Batch> batches = new ArrayList<>();
        for (int i = startNumber; i <= kit.batchesQuantity(); i++) {
            batches.add(new Batch(i, kit.id()));
        }

        batches = batchRepository.saveAll(batches);
        productService.create(kit, batches);
    }

    public void resizeBatches(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        if (oldKit.batchSize() > newKit.batchSize()) {
            productService.deleteExtra(batches, newKit.batchSize());
        } else if (oldKit.batchSize() < newKit.batchSize()) {
            productService.createExtra(oldKit, newKit, batches);
        }
    }

    public void updateBatchesQuantity(KitFull oldKit, KitFull newKit, List<Batch> batches) {
        if (oldKit.batchesQuantity() > newKit.batchesQuantity()) {
            int deleteNumber = oldKit.batchesQuantity() - newKit.batchesQuantity();
            for (int i = 1; i <= deleteNumber; i++) {
                batchRepository.deleteById(batches.get(batches.size() - i).getId());
            }
        } else if (oldKit.batchesQuantity() < newKit.batchesQuantity()) {
            create(newKit, oldKit.batchesQuantity() + 1);
        }
    }

    @Transactional
    public void deleteHistory(long id) {
        List<Long> productsId = productService.getByBatchId(id).stream()
                .map(ProductFull::id)
                .toList();

        actionService.deleteByProductsId(productsId);
        controlActionService.deleteByProductsId(productsId);
        acceptanceActionService.deleteByProducts(productsId);
        productService.deleteInactiveByBatchId(id);
    }

    protected List<BatchProgress> getProgress(Kit kit) {
        List<Batch> batches = batchRepository.findByKitId(kit.getId());

        List<BatchProgress> progress = new ArrayList<>();
        for (Batch batch : batches) {
            progress.add(computeBatchProgress(batch));
        }

        return progress;
    }

    private BatchProgress computeBatchProgress(Batch batch) {
        Map<Long, Integer> operationProgress = new HashMap<>();
        Map<ControlType, Integer> controlProgress = new HashMap<>();
        Map<BatchStat, Integer> stats = new HashMap<>();

        List<ExtendedProduct> products = productService.getExtendedByBatchId(batch.getId());
        List<ExtendedAction> actions = actionService.getExtendedByBatchId(batch.getId());
        List<ExtendedControlAction> controlActions = controlActionService.getExtendedByBatchId(batch.getId());
        List<ExtendedAcceptanceAction> acceptanceActions = acceptanceActionService.getExtendedByBatchId(batch.getId());

        List<ExtendedAction> repairActions = computeOperationProgress(actions, operationProgress);
        computeControlProgress(controlActions, repairActions, controlProgress, operationProgress);
        computeBatchStats(products, acceptanceActions, stats);

        return new BatchProgress(
                batch,
                operationProgress,
                controlProgress,
                stats
        );
    }

    private void computeBatchStats(List<ExtendedProduct> products, List<ExtendedAcceptanceAction> acceptanceActions, Map<BatchStat, Integer> stats) {
        int lockedQuantity = 0;
        int defectedQuantity = 0;

        for (ExtendedProduct product : products) {
            if (!product.getActive()) {
                defectedQuantity += 1;
            } else if (product.getLocked()) {
                lockedQuantity += 1;
            }
        }

        stats.put(LOCKED_QUANTITY, lockedQuantity);
        stats.put(DEFECTED_QUANTITY, defectedQuantity);
        stats.put(ACCEPTED_QUANTITY, acceptanceActions.size());
    }

    private void computeControlProgress(List<ExtendedControlAction> controlActions, List<ExtendedAction> repairActions, Map<ControlType, Integer> controlProgress, Map<Long, Integer> operationProgress) {
        for (ExtendedControlAction controlAction : controlActions) {
            if (!controlAction.getActive()) {
                continue;
            }
            if (controlAction.getSuccessful()) {
                controlProgress.put(controlAction.getControlType(), controlProgress.getOrDefault(controlAction.getControlType(), 0) + 1);
            } else if (!hasRepair(controlAction, repairActions)) {
                operationProgress.put(controlAction.getOperationTypeId(), operationProgress.get(controlAction.getOperationTypeId()) - 1);
            }
        }
    }

    private boolean hasRepair(ExtendedControlAction controlAction, List<ExtendedAction> repairActions) {
        for (ExtendedAction repairAction : repairActions) {
            if (repairAction.getOperationTypeId().equals(controlAction.getOperationTypeId()) &&
                    repairAction.getDoneTime().isAfter(controlAction.getDoneTime())) {
                return true;
            }
        }
        return false;
    }

    private List<ExtendedAction> computeOperationProgress(List<ExtendedAction> actions, Map<Long, Integer> operationProgress) {
        List<ExtendedAction> repairActions = new ArrayList<>();

        for (ExtendedAction action : actions) {
            if (!action.getActive()) {
                continue;
            }
            if (action.getRepair()) {
                repairActions.add(action);
            } else {
                operationProgress.put(action.getOperationTypeId(), operationProgress.getOrDefault(action.getOperationTypeId(), 0) + 1);
            }
        }

        return repairActions;
    }
}
