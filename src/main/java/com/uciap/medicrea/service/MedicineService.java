package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.MedicineRequest;
import com.uciap.medicrea.dto.MedicineResponse;
import com.uciap.medicrea.model.Medicine;
import com.uciap.medicrea.repository.MedicineRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public MedicineResponse createMedicine(MedicineRequest request) {
        Medicine medicine = new Medicine();
        applyRequest(medicine, request);
        return toResponse(medicineRepository.save(medicine));
    }

    public MedicineResponse updateStock(String id, int stock) {
        if (stock < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }
        Medicine medicine = medicineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
        medicine.setStock(stock);
        return toResponse(medicineRepository.save(medicine));
    }

    public MedicineResponse updateReorderLevel(String id, Integer reorderLevel) {
        Medicine medicine = medicineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
        medicine.setReorderLevel(reorderLevel);
        return toResponse(medicineRepository.save(medicine));
    }

    public List<MedicineResponse> getAllMedicines() {
        return medicineRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<MedicineResponse> getLowStockMedicines() {
        return medicineRepository.findLowStock().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public Medicine getMedicineEntity(String id) {
        return medicineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    private void applyRequest(Medicine medicine, MedicineRequest request) {
        medicine.setName(request.getName());
        medicine.setStock(request.getStock());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setReorderLevel(request.getReorderLevel());
    }

    private MedicineResponse toResponse(Medicine medicine) {
        MedicineResponse response = new MedicineResponse();
        response.setId(medicine.getId());
        response.setName(medicine.getName());
        response.setStock(medicine.getStock());
        response.setExpiryDate(medicine.getExpiryDate());
        response.setReorderLevel(medicine.getReorderLevel());
        return response;
    }
}
