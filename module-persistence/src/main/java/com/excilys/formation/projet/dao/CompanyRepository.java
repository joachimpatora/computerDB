package com.excilys.formation.projet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.projet.om.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
