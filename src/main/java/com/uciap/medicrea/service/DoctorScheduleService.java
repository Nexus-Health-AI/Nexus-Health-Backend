package com.uciap.medicrea.service;

import com.uciap.medicrea.dto.DoctorScheduleRequest;
import com.uciap.medicrea.dto.DoctorScheduleResponse;
import com.uciap.medicrea.model.DoctorSchedule;
import com.uciap.medicrea.repository.DoctorScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorService doctorService;

    public DoctorScheduleService(DoctorScheduleRepository doctorScheduleRepository,
                                 DoctorService doctorService) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.doctorService = doctorService;
    }

    public DoctorScheduleResponse createSchedule(DoctorScheduleRequest request) {
        doctorService.getDoctorEntity(request.getDoctorId());
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctorId(request.getDoctorId());
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setSlotMinutes(request.getSlotMinutes());
        schedule.setRoom(request.getRoom());
        schedule.setNotes(request.getNotes());
        return toResponse(doctorScheduleRepository.save(schedule));
    }

    public List<DoctorScheduleResponse> getAllSchedules() {
        return doctorScheduleRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<DoctorScheduleResponse> getSchedulesForDoctor(String doctorId) {
        doctorService.getDoctorEntity(doctorId);
        return doctorScheduleRepository.findByDoctorId(doctorId).stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private DoctorScheduleResponse toResponse(DoctorSchedule schedule) {
        DoctorScheduleResponse response = new DoctorScheduleResponse();
        response.setId(schedule.getId());
        response.setDoctorId(schedule.getDoctorId());
        response.setDayOfWeek(schedule.getDayOfWeek());
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());
        response.setSlotMinutes(schedule.getSlotMinutes());
        response.setRoom(schedule.getRoom());
        response.setNotes(schedule.getNotes());
        return response;
    }
}
