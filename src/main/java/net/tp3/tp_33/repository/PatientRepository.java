package net.tp3.tp_33.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.tp3.tp_33.entite.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByFirstNameContainingIgnoreCase(String key, Pageable pageable);
    Optional<Patient> findById(Long id);
}

