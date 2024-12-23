package com.example.service;
import com.example.models.patient;
import com.example.repository.patientRepo;
import com.example.DTO.appointmentDTO;
import com.example.exceptions.PatientNotFoundException;
import org.glassfish.jersey.internal.util.collection.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.service.appointmentClient;

import java.util.List;
import java.util.Optional;


@Service
public class patientServ {
    private final patientRepo patientRepository;
    private final appointmentClient appointmentClient;
    @Autowired
    public patientServ(patientRepo patientRepository, appointmentClient appointmentClient) {
        this.patientRepository = patientRepository;
        this.appointmentClient = appointmentClient;
    }

    @Transactional
    public patient createPatient(patient newPatient) {
        System.out.println("patient created");
        patient savedPatient = patientRepository.save(newPatient);
        return savedPatient;
    }

    public patient getPatientById(Long id) {
        System.out.println("Fetching patient with ID: " + id);
        Optional<patient> patientOpt = patientRepository.findById(id);

        if (patientOpt.isPresent()) {
            return patientOpt.get();
        } else {
            System.err.println("Patient with ID " + id + " not found");
            throw new PatientNotFoundException("Patient with ID " + id + " not found");
        }
    }
}
