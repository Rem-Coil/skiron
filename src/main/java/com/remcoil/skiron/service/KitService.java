package com.remcoil.skiron.service;

import com.remcoil.skiron.database.entity.*;
import com.remcoil.skiron.database.entity.view.ExtendedAcceptanceAction;
import com.remcoil.skiron.database.entity.view.ExtendedAction;
import com.remcoil.skiron.database.entity.view.ExtendedControlAction;
import com.remcoil.skiron.database.entity.view.ExtendedProduct;
import com.remcoil.skiron.database.repository.*;
import com.remcoil.skiron.exception.EntryDoesNotExistException;
import com.remcoil.skiron.model.operation.OperationTypeFull;
import com.remcoil.skiron.model.progress.BatchProgress;
import com.remcoil.skiron.model.progress.KitBriefProgress;
import com.remcoil.skiron.model.kit.KitFull;
import com.remcoil.skiron.model.kit.KitPostRequest;
import com.remcoil.skiron.model.progress.KitProgress;
import com.remcoil.skiron.model.progress.KitStat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.remcoil.skiron.model.progress.KitStat.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
@AllArgsConstructor
public class KitService {
    private final BatchService batchService;
    private final KitRepository kitRepository;
    private final ProductService productService;
    private final SpecificationService specificationService;
    private final OperationTypeService operationTypeService;

    private final ActionService actionService;
    private final ControlActionService controlActionService;
    private final AcceptanceActionService acceptanceActionService;


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

    @Transactional
    public List<KitBriefProgress> getProgress() {
        List<KitBriefProgress> progress = new ArrayList<>();

        List<Specification> specifications = specificationService.getAllFull();
        for (Specification specification : specifications) {
            List<Kit> kits = kitRepository.findAllBySpecificationId(specification.getId());
            for (Kit kit : kits) {
                progress.add(computeKitBriefProgress(specification, kit));
            }
        }
        return progress;
    }

    private KitBriefProgress computeKitBriefProgress(Specification specification, Kit kit) {
        Map<KitStat, Integer> stats = new HashMap<>();
        List<OperationType> operationTypes = specification.getOperationTypes().stream()
                .sorted(Comparator.comparing(OperationType::getSequenceNumber))
                .toList();

        OperationType firstOperationType = operationTypes.get(0);
        OperationType lastOperationType = operationTypes.get(operationTypes.size() - 1);

        computeAcceptance(stats, kit);
        computeLockedAndDefected(stats, kit);
        List<ExtendedAction> repairActions = computeProductsStatsAndGetRepair(stats, kit, firstOperationType, lastOperationType);
        Map<ControlType, Integer> controlProgress = computeControlStats(stats, kit, repairActions, lastOperationType);

        return new KitBriefProgress(
                specification,
                kit,
                stats,
                controlProgress
        );
    }

    private Map<ControlType, Integer> computeControlStats(
            Map<KitStat, Integer> stats,
            Kit kit,
            List<ExtendedAction> repairActions,
            OperationType lastOperation
    ) {
        List<ExtendedControlAction> controlActions = controlActionService.getExtendedByKitId(kit.getId());
        Map<ControlType, Integer> controlProgress = ControlType.getCountMap();

        for (ExtendedControlAction controlAction : controlActions) {
            if (controlAction.getSuccessful()) {
                controlProgress.put(controlAction.getControlType(), controlProgress.get(controlAction.getControlType()) + 1);
            }
            if (controlAction.getOperationTypeId().equals(lastOperation.getId())
                    && controlAction.getNeedRepair()
                    && repairExist(controlAction, repairActions)
            ) {
                stats.put(PRODUCTS_DONE, stats.get(PRODUCTS_DONE) - 1);
            }
        }

        return controlProgress;
    }

    private boolean repairExist(ExtendedControlAction controlAction, List<ExtendedAction> repairActions) {
        for (ExtendedAction repairAction : repairActions) {
            if (repairAction.getProductId().equals(controlAction.getProductId())
                    && repairAction.getOperationTypeId().equals(controlAction.getOperationTypeId())
                    && repairAction.getDoneTime().isAfter(controlAction.getDoneTime())
            ) {
                return true;
            }
        }
        return false;
    }

    private List<ExtendedAction> computeProductsStatsAndGetRepair(Map<KitStat, Integer> stats, Kit kit, OperationType firstOperationType, OperationType lastOperationType) {
        int productsInWork = 0;
        int productsDone = 0;
        List<ExtendedAction> actions = actionService.getExtendedByKitId(kit.getId());
        List<ExtendedAction> repairActions = new ArrayList<>();

        for (ExtendedAction action : actions) {
            if (!action.getActive()) {
                continue;
            }
            if (action.getRepair()) {
                repairActions.add(action);
                continue;
            }
            productsInWork += action.getOperationTypeId().equals(firstOperationType.getId()) ? 1 : 0;
            productsDone += action.getOperationTypeId().equals(lastOperationType.getId()) ? 1 : 0;
        }

        stats.put(PRODUCTS_IN_WORK, productsInWork);
        stats.put(PRODUCTS_DONE, productsDone);
        return repairActions;
    }

    private void computeLockedAndDefected(Map<KitStat, Integer> stats, Kit kit) {
        List<ExtendedProduct> products = productService.getExtendedByKitId(kit.getId());
        int lockedQuantity = 0;
        int defectedQuantity = 0;

        for (ExtendedProduct product : products) {
            if (product.getActive() && product.getLocked()) {
                lockedQuantity += 1;
            } else if (!product.getActive()) {
                defectedQuantity += 1;
            }
        }

        stats.put(LOCKED_QUANTITY, lockedQuantity);
        stats.put(DEFECTED_QUANTITY, defectedQuantity);
    }

    private void computeAcceptance(Map<KitStat, Integer> stats, Kit kit) {
        int batchesAccepted = 0;

        List<ExtendedAcceptanceAction> acceptanceActions = acceptanceActionService.getExtendedByKitId(kit.getId());
        Map<Long, Long> acceptanceCountByBatch = acceptanceActions.stream()
                .filter(ExtendedAcceptanceAction::getActive)
                .collect(groupingBy(ExtendedAcceptanceAction::getBatchId, counting()));

        for (Long count : acceptanceCountByBatch.values()) {
            batchesAccepted += (100 * count / kit.getBatchSize()) >= kit.getAcceptancePercentage() ? 1 : 0;
        }

        stats.put(BATCHES_ACCEPTED, batchesAccepted);
    }

    @Transactional
    public KitProgress getProgressById(long id) {
        Kit kit = kitRepository.findById(id).orElseThrow(() -> new EntryDoesNotExistException("Not Found"));
        List<OperationTypeFull> operationTypes = operationTypeService.getBySpecificationId(kit.getId());
        List<BatchProgress> batchProgresses = batchService.getProgress(kit);

        return new KitProgress(kit, operationTypes, batchProgresses);
    }
}
