package com.excilys.formation.projet.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.projet.dao.CompanyDao;
import com.excilys.formation.projet.om.Company;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyDao companyDao;
	
	public List<Company> getAll() throws SQLException {
		return companyDao.getAll();
	}	
	
	public Company find(Long companyid)
	{
		return companyDao.find(companyid);
	}
	
	public Company find(String companyname)
	{
		return companyDao.find(companyname);
	}
	
}