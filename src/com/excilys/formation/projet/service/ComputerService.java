package com.excilys.formation.projet.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.projet.dao.ComputerDao;
import com.excilys.formation.projet.dao.DAOFactory;
import com.excilys.formation.projet.om.Computer;

public class ComputerService {
	
	private ComputerDao computerDao = DAOFactory.getInstance().getComputerDAO();
	private static ComputerService  _instance = null;
	
	synchronized public static ComputerService getInstance() {
		if (_instance == null) {
			_instance = new ComputerService ();
		}
		return _instance;
	}
	
	public void update(Computer computer) throws SQLException {
		computerDao.update(computer);
	}
	
	public void delete(Long id) throws SQLException {
		computerDao.delete(id);
	}

	public void add(Computer computer) throws SQLException {
		computerDao.add(computer);
	}
	
	public Computer get(Long id) throws SQLException {
		return computerDao.get(id);
	}
	
	public List<Computer> getAll(Long offset, Long noOfRecords, String searchStr, String orderBy) throws SQLException {
		return computerDao.getAll(offset, noOfRecords, searchStr, orderBy);
	}
	
	public int getCount() {
		return computerDao.getNbOfComputers();
	}
}