package com.excilys.formation.projet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {

	private static ServiceFactory instance = null;

	private ComputerService computerService = null;
	private CompanyService companyService = null;

	public final static ServiceFactory getInstance() {
		if (ServiceFactory.instance == null) {
			synchronized (ServiceFactory.class) {
				if (ServiceFactory.instance == null) {
					ServiceFactory.instance = new ServiceFactory();
				}
			}
		}
		return ServiceFactory.instance;
	}

	public ComputerService getComputerService() {
		return computerService;
	}
	
	private ServiceFactory() {
	
		this.computerService = new ComputerService();
		this.companyService = new CompanyService();
	}

	public CompanyService getCompanyService() {
		// TODO Auto-generated method stub
		return companyService;
	}

	
}
