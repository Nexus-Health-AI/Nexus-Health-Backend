package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.MedicalReportResponse;
import com.uciap.medicrea.model.LabResult;
import com.uciap.medicrea.model.LabTest;
import com.uciap.medicrea.model.MedicalReport;
import com.uciap.medicrea.model.Patient;
import com.uciap.medicrea.model.Prescription;
import com.uciap.medicrea.report.ReportGenerator;
import com.uciap.medicrea.repository.MedicalReportRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalReportService {

    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final LabResultService labResultService;
    private final LabTestService labTestService;
    private final DoctorService doctorService;
    private final MedicalReportRepository medicalReportRepository;
    private final ReportGenerator reportGenerator;
    private final ObjectMapper objectMapper;

    private record GeneratedReport(
            Patient patient,
            String reportId,
            LocalDateTime generatedAt,
            String reportHash,
            String html
    ) {
    }

    public MedicalReportResponse generatePatientReport(String patientId) {
        GeneratedReport generatedReport = buildReport(patientId);
        Path filePath = writeReportToDisk(generatedReport.reportId(), generatedReport.html());
        MedicalReport report = new MedicalReport();
        report.setPatientId(generatedReport.patient().getId());
        report.setFilePath(filePath.toAbsolutePath().toString());
        report.setHash(generatedReport.reportHash());
        report.setCreatedAt(generatedReport.generatedAt());
        report.setHtml(generatedReport.html());
        MedicalReport saved = medicalReportRepository.save(report);
        return toResponse(saved);
    }

    public String generatePatientReportHtml(String patientId) {
        GeneratedReport generatedReport = buildReport(patientId);
        return generatedReport.html();
    }

    private GeneratedReport buildReport(String patientId) {
        Patient patient = patientService.getPatientEntity(patientId);
        List<Prescription> prescriptions = prescriptionService.getPrescriptionEntitiesForPatient(patientId);
        List<LabResult> labResults = labResultService.getLabResultEntitiesForPatient(patientId);

        String reportId = UUID.randomUUID().toString();
        LocalDateTime generatedAt = LocalDateTime.now();

        Map<String, String> doctorNames = loadDoctorNames(prescriptions);
        Map<String, String> labNormalRanges = loadLabNormalRanges(labResults);

        String signatureSource = composeSignatureSource(patient, prescriptions, labResults, reportId, generatedAt);
        String reportHash = computeHash(signatureSource);

        String html = reportGenerator.generateReport(patient, prescriptions, labResults,
            doctorNames, labNormalRanges, reportId, generatedAt, reportHash);

        log.info("Report generated for patient {}", patientId);

        return new GeneratedReport(patient, reportId, generatedAt, reportHash, html);
    }
    private Map<String, String> loadDoctorNames(List<Prescription> prescriptions) {
        Map<String, String> names = new HashMap<>();
        prescriptions.forEach(prescription -> {
            String doctorId = prescription.getDoctorId();
            if (doctorId != null && !names.containsKey(doctorId)) {
                names.put(doctorId, doctorService.getDoctorEntity(doctorId).getFullName());
            }
        });
        return names;
    }

    private Map<String, String> loadLabNormalRanges(List<LabResult> labResults) {
        Map<String, String> ranges = new HashMap<>();
        labResults.forEach(result -> {
            String testName = result.getTestName();
            if (testName != null && !ranges.containsKey(testName)) {
                LabTest labTest = labTestService.getByName(testName);
                ranges.put(testName, labTest != null ? labTest.getNormalRange() : "N/A");
            }
        });
        return ranges;
    }

    private String composeSignatureSource(Patient patient,
                                          List<Prescription> prescriptions,
                                          List<LabResult> labResults,
                                          String reportId,
                                          LocalDateTime generatedAt) {
        StringBuilder builder = new StringBuilder();
        builder.append(reportId).append('|').append(patient.getId()).append('|').append(generatedAt);
        prescriptions.stream()
            .sorted((a, b) -> {
                if (a.getIssuedDate() == null && b.getIssuedDate() == null) {
                    return 0;
                }
                if (a.getIssuedDate() == null) {
                    return 1;
                }
                if (b.getIssuedDate() == null) {
                    return -1;
                }
                return a.getIssuedDate().compareTo(b.getIssuedDate());
            })
            .forEach(prescription -> builder.append('|')
                .append(prescription.getIssuedDate())
                .append('|').append(prescription.getMedicineName())
                .append('|').append(prescription.getDosage())
                .append('|').append(prescription.getFrequency()));
        labResults.stream()
            .sorted((a, b) -> {
                if (a.getResultDate() == null && b.getResultDate() == null) {
                    return 0;
                }
                if (a.getResultDate() == null) {
                    return 1;
                }
                if (b.getResultDate() == null) {
                    return -1;
                }
                return a.getResultDate().compareTo(b.getResultDate());
            })
            .forEach(result -> builder.append('|')
                .append(result.getResultDate())
                .append('|').append(result.getTestName())
                .append('|').append(result.getResult()));
        return builder.toString();
    }

    private String computeHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to compute hash", e);
        }
    }

    private Path writeReportToDisk(String reportId, String html) {
        try {
            Path reportsDir = Paths.get("reports");
            Files.createDirectories(reportsDir);
            Path filePath = reportsDir.resolve(reportId + ".html");
            Files.writeString(filePath, html, StandardCharsets.UTF_8);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report file", e);
        }
    }

    private MedicalReportResponse toResponse(MedicalReport report) {
        MedicalReportResponse response = new MedicalReportResponse();
        response.setId(report.getId());
        response.setPatientId(report.getPatientId());
        response.setFilePath(report.getFilePath());
        response.setHash(report.getHash());
        response.setCreatedAt(report.getCreatedAt());
        response.setHtml(report.getHtml());
        return response;
    }

    public List<MedicalReportResponse> getReportByPatientId(String patientId) {

        List<MedicalReport> reports =
                medicalReportRepository.findByPatientId(patientId);

        if (reports.isEmpty()) {
            log.warn("No medical report found for patient {}", patientId);
        }

        return reports.stream()
                .map(this::toResponse)
                .toList();
    }

}
