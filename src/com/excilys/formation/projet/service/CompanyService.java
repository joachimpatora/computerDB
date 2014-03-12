package com.excilys.formation.projet.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.projet.dao.CompanyDao;
import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.om.Company;

public class CompanyService {
	private CompanyDao companyDao = DAOFactory.getInstance().getCompanyDAO();
	private static CompanyService _instance = null;
	
	synchronized public static CompanyService getInstance() {
		if (_instance == null) {
			_instance = new CompanyService();
		}
		return _instance;
	}
	
	public List<Company> getAll() throws SQLException {
		return companyDao.getAll();
	}	
	
}