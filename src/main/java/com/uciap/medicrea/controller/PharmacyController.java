package com.uciap.medicrea.controller;

import com.uciap.medicrea.dto.MedicineBatchRequest;
import com.uciap.medicrea.dto.MedicineBatchResponse;
import com.uciap.medicrea.dto.MedicineReorderLevelRequest;
import com.uciap.medicrea.dto.MedicineRequest;
import com.uciap.medicrea.dto.MedicineResponse;
import com.uciap.medicrea.dto.MedicineStockUpdateRequest;
import com.uciap.medicrea.dto.PrescriptionRequest;
import com.uciap.medicrea.dto.PrescriptionResponse;
import com.uciap.medicrea.service.MedicineBatchService;
import com.uciap.medicrea.service.MedicineService;
import com.uciap.medicrea.service.PrescriptionService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PharmacyController {

    private final MedicineService medicineService;
    private final PrescriptionService prescriptionService;
    private final MedicineBatchService medicineBatchService;

    public PharmacyController(MedicineService medicineService,
                              PrescriptionService prescriptionService,
                              MedicineBatchService medicineBatchService) {
        this.medicineService = medicineService;
        this.prescriptionService = prescriptionService;
        this.medicineBatchService = medicineBatchService;
    }

    @PostMapping("/medicines")
    public MedicineResponse createMedicine(@RequestBody MedicineRequest request) {
        return medicineService.createMedicine(request);
    }

    @GetMapping("/medicines")
    public List<MedicineResponse> getMedicines() {
        return medicineService.getAllMedicines();
    }

    @PutMapping("/medicines/{id}/stock")
    public MedicineResponse updateStock(@PathVariable String id, @RequestBody MedicineStockUpdateRequest request) {
        return medicineService.updateStock(id, request.getStock());
    }

    @PutMapping("/medicines/{id}/reorder-level")
    public MedicineResponse updateReorderLevel(@PathVariable String id, @RequestBody MedicineReorderLevelRequest request) {
        return medicineService.updateReorderLevel(id, request.getReorderLevel());
    }

    @GetMapping("/medicines/low-stock")
    public List<MedicineResponse> getLowStockMedicines() {
        return medicineService.getLowStockMedicines();
    }

    @PostMapping("/medicines/batches")
    public MedicineBatchResponse createBatch(@RequestBody MedicineBatchRequest request) {
        return medicineBatchService.createBatch(request);
    }

    @GetMapping("/medicines/{medicineId}/batches")
    public List<MedicineBatchResponse> getBatchesForMedicine(@PathVariable String medicineId) {
        return medicineBatchService.getBatchesForMedicine(medicineId);
    }

    @PostMapping("/prescriptions")
    public PrescriptionResponse createPrescription(@RequestBody PrescriptionRequest request) {
        return prescriptionService.createPrescription(request);
    }

    @GetMapping("/prescriptions/patient/{patientId}")
    public List<PrescriptionResponse> getPrescriptions(@PathVariable String patientId) {
        return prescriptionService.getPrescriptionsForPatient(patientId);
    }
}
