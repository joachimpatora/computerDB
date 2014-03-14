package com.excilys.formation.projet.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.projet.dao.ComputerDao;
import com.excilys.formation.projet.om.Computer;

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDao computerDao;
	
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
	
	public List<Computer> getAll()
	{
		return computerDao.getAll();
	}
	
	public int getCount() {
		return computerDao.getNbOfComputers();
	}
}