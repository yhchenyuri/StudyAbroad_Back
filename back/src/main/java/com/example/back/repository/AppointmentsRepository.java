package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.model.Appointments;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
	 boolean existsByEmail(String email); // 防重複
}

