package com.excilys.formation.projet.service;

import com.excilys.formation.projet.dao.DAOFactory;

public class Service {
	private DAOFactory daofactory = null;
	
	public Service()
	{
		daofactory = DAOFactory.getInstance();
	}
	
	public DAOFactory getDaoFactory()
	{
		return daofactory;
	}
}
