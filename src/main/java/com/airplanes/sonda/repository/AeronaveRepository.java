package com.airplanes.sonda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airplanes.sonda.model.Aeronave;


public interface AeronaveRepository extends JpaRepository<Aeronave, Long>{
	
	List<Aeronave> findByNome(String parametro);
	
	List<Aeronave> findById(String parametro);

}
