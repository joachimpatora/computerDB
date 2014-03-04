package com.excilys.formation.projet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.excilys.formation.projet.dao.CompanyDao;
import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.om.Company;

public class CompanyService {
	private CompanyDao companyDao = DAOFactory.getInstance().getCompanyDAO();
	
	
	public List<Company> getAll() {
		return companyDao.getAll();
	}	
	
}