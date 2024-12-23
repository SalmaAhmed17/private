package com.example.repository;

import org.springframework.stereotype.Repository;
import com.example.models.patient;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface patientRepo extends JpaRepository<patient, Long> {

}

