package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
