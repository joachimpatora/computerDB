package com.excilys.formation.projet.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
	
	Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private static volatile DAOFactory _instance = null;

	synchronized public final static DAOFactory getInstance() {
		if (_instance == null) {
			synchronized (DAOFactory.class) {
				if (_instance == null) {
					_instance = new DAOFactory();
				}
			}
		}
		return _instance;
	}

	private DAOFactory() {
		
	}

	public ComputerDao getComputerDAO() {
		return ComputerDao.getInstance();
	}

	public CompanyDao getCompanyDAO() {
		return CompanyDao.getInstance();
	}
}
