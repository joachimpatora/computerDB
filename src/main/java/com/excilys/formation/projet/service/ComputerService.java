package com.excilys.formation.projet.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.projet.dao.ComputerDao;
import com.excilys.formation.projet.dao.MonitorDbDao;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.ComputerListWrapper;

@Service
public class ComputerService {
	
	@Autowired
	private MonitorDbDao monitor;
	
	@Autowired
	private ComputerDao computerDao;

	@Transactional
	public void update(Computer computer) throws SQLException {
		computerDao.update(computer);
		monitor.addLog(0L, "Updating "+computer.getId());
	}
	
	@Transactional
	public void delete(Long id) {
		monitor.addLog(0L, "Deleting "+id);
		computerDao.delete(id);
		
	}

	@Transactional
	public void add(Computer computer) throws SQLException {
		computerDao.add(computer);
		monitor.addLog(0L, "Add "+computer.getId());
	}
	
	public Computer get(Long id) throws SQLException {
		return computerDao.get(id);
	}
	
	public ComputerListWrapper getAll(Long offset, Long noOfRecords, String searchStr, String orderBy) throws SQLException {
		ComputerListWrapper clw = new ComputerListWrapper(computerDao.getAll(offset, noOfRecords, searchStr, orderBy),computerDao.getNbOfComputers());
		monitor.addLog(0L, "GettingAll");
		return clw;
	}
	
	public List<Computer> getAll()
	{
		return computerDao.getAll();
	}
	
	public int getCount() {
		return computerDao.getNbOfComputers();
	}
}