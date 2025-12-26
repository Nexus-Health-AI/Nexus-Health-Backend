package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.DashboardSummaryResponse;
import com.uciap.medicrea.repository.AdmissionRepository;
import com.uciap.medicrea.repository.AppointmentRepository;
import com.uciap.medicrea.repository.BedRepository;
import com.uciap.medicrea.repository.BloodUnitRepository;
import com.uciap.medicrea.repository.DoctorRepository;
import com.uciap.medicrea.repository.EmergencyCaseRepository;
import com.uciap.medicrea.repository.InsuranceClaimRepository;
import com.uciap.medicrea.repository.LabOrderRepository;
import com.uciap.medicrea.repository.MedicineRepository;
import com.uciap.medicrea.repository.NurseRepository;
import com.uciap.medicrea.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final AppointmentRepository appointmentRepository;
    private final AdmissionRepository admissionRepository;
    private final BedRepository bedRepository;
    private final LabOrderRepository labOrderRepository;
    private final MedicineRepository medicineRepository;
    private final EmergencyCaseRepository emergencyCaseRepository;
    private final BloodUnitRepository bloodUnitRepository;
    private final InsuranceClaimRepository insuranceClaimRepository;

    public DashboardService(PatientRepository patientRepository,
                            DoctorRepository doctorRepository,
                            NurseRepository nurseRepository,
                            AppointmentRepository appointmentRepository,
                            AdmissionRepository admissionRepository,
                            BedRepository bedRepository,
                            LabOrderRepository labOrderRepository,
                            MedicineRepository medicineRepository,
                            EmergencyCaseRepository emergencyCaseRepository,
                            BloodUnitRepository bloodUnitRepository,
                            InsuranceClaimRepository insuranceClaimRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.admissionRepository = admissionRepository;
        this.bedRepository = bedRepository;
        this.labOrderRepository = labOrderRepository;
        this.medicineRepository = medicineRepository;
        this.emergencyCaseRepository = emergencyCaseRepository;
        this.bloodUnitRepository = bloodUnitRepository;
        this.insuranceClaimRepository = insuranceClaimRepository;
    }

    public DashboardSummaryResponse getSummary() {
        DashboardSummaryResponse response = new DashboardSummaryResponse();
        response.setTotalPatients(patientRepository.count());
        response.setTotalDoctors(doctorRepository.count());
        response.setTotalNurses(nurseRepository.count());
        response.setTotalAppointments(appointmentRepository.count());
        response.setActiveAdmissions(
            admissionRepository.findByStatus("ADMITTED").size() +
            admissionRepository.findByStatus("TRANSFERRED").size()
        );
        response.setTotalBeds(bedRepository.count());
        response.setOccupiedBeds(bedRepository.findByStatus("OCCUPIED").size());
        response.setPendingLabOrders(labOrderRepository.findByStatus("ORDERED").size());
        response.setLowStockMedicines(medicineRepository.findLowStock().size());
        response.setActiveEmergencyCases(emergencyCaseRepository.findByStatus("TRIAGED").size());
        response.setAvailableBloodUnits(bloodUnitRepository.findByStatus("AVAILABLE").size());
        response.setPendingInsuranceClaims(insuranceClaimRepository.findByStatus("SUBMITTED").size());
        return response;
    }
}
