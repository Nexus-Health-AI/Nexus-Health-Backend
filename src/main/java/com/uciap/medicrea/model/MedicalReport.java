package com.uciap.medicrea.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String patientId;
    private String filePath;
    private String hash;
    private LocalDateTime createdAt;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String html;


    public MedicalReport() {
    }

}
