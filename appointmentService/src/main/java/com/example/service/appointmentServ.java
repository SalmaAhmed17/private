package com.example.service;


import com.example.models.appointment;
import com.example.models.appointmentStatus;
import com.example.repository.appointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class appointmentServ {
    @Autowired
    private appointmentRepo repository;


    public appointment createAppointment(appointment newAppointment) {
        System.out.println("hi from appointment");
        Optional<appointment> existingAppointment =
                repository.findByDoctorIdAndAppointmentDate(newAppointment.getDoctorId(), newAppointment.getAppointmentDate());

        if (existingAppointment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment slot is already reserved.");        }

        newAppointment.setStatus(appointmentStatus.BOOKED);
        return repository.save(newAppointment);
    }

    @Transactional
    public appointment updateAppointmentDate(Long appointmentId, LocalDateTime newDate) {
        appointment existingAppointment = repository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));

        Optional<appointment> conflict = repository.findByDoctorIdAndAppointmentDate(existingAppointment.getDoctorId(), newDate);
        if (conflict.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment slot is already reserved.");         }

        if (existingAppointment.getStatus().equals(appointmentStatus.CANCELLED)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Appointment is already cancelled.");
        }
        existingAppointment.setAppointmentDate(newDate);
        existingAppointment.setStatus(appointmentStatus.RESCHEDULED);
        return repository.save(existingAppointment);
    }

    public appointment deleteAppointment(Long appointmentId) {
        appointment cancelledAppointment = repository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
        cancelledAppointment.setStatus(appointmentStatus.CANCELLED);
        return repository.save(cancelledAppointment);
    }
    public appointment getAppointmentDetails(Long appointmentId) {
        Optional<appointment> appointment = repository.findById(appointmentId);

        // Return the appointment if found, else return null
        return appointment.orElse(null);
    }
    public List<appointment> findAppointmentsByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }
}


