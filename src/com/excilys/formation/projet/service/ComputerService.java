package com.excilys.formation.projet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.dao.ComputerDao;
import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.om.Computer;

public class ComputerService {
	
	private ComputerDao computerDao = DAOFactory.getInstance().getComputerDAO();
	
	
	public void update(Computer computer) {
		computerDao.update(computer);
	}
	
	public void delete(Long id) {
		computerDao.delete(id);
	}

	public void add(Computer computer) {
		computerDao.add(computer);
	}
	
	public Computer get(Long id) {
		return computerDao.get(id);
	}
	
	public List<Computer> getAll(Long offset, Long noOfRecords, String searchStr, String orderBy) {
		return computerDao.getAll(offset, noOfRecords, searchStr, orderBy);
	}
	
	public int getCount() {
		return computerDao.getNbOfComputers();
	}
}