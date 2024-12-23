package com.example.controller;



import com.example.models.appointment;
import com.example.service.appointmentServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class appointmentController {
    @Autowired
    private final appointmentServ service;
    @Autowired
    public appointmentController(appointmentServ service) {
        this.service = service;
    }
    @PostMapping("/{patientId}/appointments")
    public appointment createAppointment(
            @PathVariable Long patientId,
            @RequestBody appointment newAppointment) {
        System.out.println("hello from the ctrl");
        newAppointment.setPatientId(patientId);
        return service.createAppointment(newAppointment);
    }

    @PutMapping("/{patientId}/appointments/{appointmentId}")
    public appointment updateAppointment(
            @PathVariable Long patientId,
            @PathVariable Long appointmentId,
            @RequestBody CharSequence newDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDate = LocalDateTime.parse(newDate, formatter);
        return service.updateAppointmentDate(appointmentId, parsedDate);

    }

    @DeleteMapping("/{patientId}/appointments/{appointmentId}")
    public appointment deleteAppointment(@PathVariable Long appointmentId) {
        service.deleteAppointment(appointmentId);
        return service.deleteAppointment(appointmentId);    }

   @GetMapping("/{patientId}/appointments")
   public List<appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
       return service.findAppointmentsByPatientId(patientId);
   }
}