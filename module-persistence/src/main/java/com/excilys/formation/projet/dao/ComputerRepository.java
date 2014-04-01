package com.excilys.formation.projet.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.projet.om.Computer;


public interface ComputerRepository extends JpaRepository<Computer, Long>{
	public List<Computer> findByNameContaining(String computerName, Pageable pageable);
	
	public int countByNameContaining(String search);
}
