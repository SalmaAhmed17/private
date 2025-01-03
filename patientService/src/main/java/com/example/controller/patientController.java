package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.patientServ;
import com.example.models.patient;
import com.example.DTO.appointmentDTO;
import com.example.service.appointmentClient;
import com.example.exceptions.PatientNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class patientController {
    @Autowired
    private final patientServ patientService;
    private final appointmentDTO appointmentDTO;

    @Autowired
    private appointmentClient appointmentClient;

    @Autowired
    public patientController(patientServ patientService, appointmentDTO appointmentDTO) {
        this.patientService = patientService;
        this.appointmentDTO = appointmentDTO;
    }

    @PostMapping("/create")
    public patient createPatient(@RequestBody patient newPatient) {
        return patientService.createPatient(newPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<patient> getPatientById(@PathVariable Long id) {
        try {
            patient patient = patientService.getPatientById(id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } catch (PatientNotFoundException ex) {
            throw new PatientNotFoundException("Patient with ID " + id + " not found");
        }
    }

    @PostMapping("/{patientId}/appointments")
    public appointmentDTO bookAppointment(@PathVariable Long patientId, @RequestBody appointmentDTO newAppointment) {
        return appointmentClient.createAppointment(patientId, newAppointment);
    }

    @PutMapping("/{patientId}/appointments/{appointmentId}")
    public appointmentDTO rescheduleAppointment(@PathVariable Long patientId, @PathVariable Long appointmentId, @RequestBody CharSequence newDate) {
        return appointmentClient.updateAppointment(patientId, appointmentId, "\"" + newDate + "\"");
    }

    @DeleteMapping("/{patientId}/appointments/{appointmentId}")
    public appointmentDTO cancelAppointment(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        return appointmentClient.deleteAppointment(patientId, appointmentId);
    }

    @GetMapping("{patientId}/appointments")
    public List<appointmentDTO> getPatientAppointments(@PathVariable Long patientId) {
        try{        return appointmentClient.getAppointmentsByPatientId(patientId);
        }
        catch (PatientNotFoundException ex){
            throw new PatientNotFoundException("Patient with ID " + patientId + " not found");
         }
        }
    }