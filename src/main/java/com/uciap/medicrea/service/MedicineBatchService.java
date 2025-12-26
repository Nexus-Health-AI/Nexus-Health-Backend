package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.MedicineBatchRequest;
import com.uciap.medicrea.dto.MedicineBatchResponse;
import com.uciap.medicrea.model.MedicineBatch;
import com.uciap.medicrea.repository.MedicineBatchRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MedicineBatchService {

    private final MedicineBatchRepository medicineBatchRepository;
    private final MedicineService medicineService;

    public MedicineBatchService(MedicineBatchRepository medicineBatchRepository,
                                MedicineService medicineService) {
        this.medicineBatchRepository = medicineBatchRepository;
        this.medicineService = medicineService;
    }

    public MedicineBatchResponse createBatch(MedicineBatchRequest request) {
        medicineService.getMedicineEntity(request.getMedicineId());
        MedicineBatch batch = new MedicineBatch();
        batch.setMedicineId(request.getMedicineId());
        batch.setBatchNumber(request.getBatchNumber());
        batch.setExpiryDate(request.getExpiryDate());
        batch.setQuantity(request.getQuantity());
        batch.setReceivedDate(request.getReceivedDate());
        batch.setSupplier(request.getSupplier());
        batch.setNotes(request.getNotes());
        return toResponse(medicineBatchRepository.save(batch));
    }

    public List<MedicineBatchResponse> getBatchesForMedicine(String medicineId) {
        medicineService.getMedicineEntity(medicineId);
        return medicineBatchRepository.findByMedicineId(medicineId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private MedicineBatchResponse toResponse(MedicineBatch batch) {
        MedicineBatchResponse response = new MedicineBatchResponse();
        response.setId(batch.getId());
        response.setMedicineId(batch.getMedicineId());
        response.setBatchNumber(batch.getBatchNumber());
        response.setExpiryDate(batch.getExpiryDate());
        response.setQuantity(batch.getQuantity());
        response.setReceivedDate(batch.getReceivedDate());
        response.setSupplier(batch.getSupplier());
        response.setNotes(batch.getNotes());
        return response;
    }
}
