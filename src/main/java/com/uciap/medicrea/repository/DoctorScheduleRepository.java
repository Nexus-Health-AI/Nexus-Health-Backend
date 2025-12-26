package com.uciap.medicrea.repository;

import com.uciap.medicrea.model.DoctorSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, String> {
    List<DoctorSchedule> findByDoctorId(String doctorId);
}
