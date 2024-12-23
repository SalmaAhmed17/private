package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.models.appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface appointmentRepo extends JpaRepository<appointment, Long> {
    List<appointment> findByPatientId(Long patientId);
    Optional<appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);
}
