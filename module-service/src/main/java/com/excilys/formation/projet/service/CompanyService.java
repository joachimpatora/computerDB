package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.projet.dao.CompanyRepository;
import com.excilys.formation.projet.om.Company;

@Service
@Transactional
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyDao;
	
	public CompanyService()
	{
		super();
	}
	
	public List<Company> getAll() {
		return companyDao.findAll();
	}	
}