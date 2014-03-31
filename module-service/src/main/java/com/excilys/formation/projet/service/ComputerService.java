package com.excilys.formation.projet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.projet.dao.ComputerRepository;
import com.excilys.formation.projet.dao.MonitorDbDao;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.ComputerListWrapper;

@Service
@Transactional
public class ComputerService {

	@Autowired
	private MonitorDbDao monitor;

	@Autowired
	private ComputerRepository computerDao;

	public Computer get(Long id) {
		return computerDao.findOne(id);
	}

	public void update(Computer computer) {
		computerDao.save(computer);
	}

	public void delete(Long id) {
		computerDao.delete(id);
	}

	public void create(Computer computer) {
		computerDao.save(computer);
	}

	public List<Computer> findAll() {
		return computerDao.findAll();
	}

	public Page<Computer> findAll(Pageable pageable) {
		return computerDao.findAll(pageable);
	}

	public ComputerListWrapper findAllByName(Long offset, Long noOfRecords,
			String search, String orderBy) {
		Direction direction = Direction.ASC;
		String columnToOrder = "id";
		Long pageNumber = offset / noOfRecords;
		if (orderBy != null) {
			if (orderBy.equals("orderByNameAsc")) {
				direction = Direction.ASC;
				columnToOrder = "name";
			} else if (orderBy.equals("orderByNameDesc")) {
				direction = Direction.DESC;
				columnToOrder = "name";
			} else if (orderBy.equals("orderByIntroAsc")) {
				direction = Direction.ASC;
				columnToOrder = "introduced";
			} else if (orderBy.equals("orderByIntroDesc")) {
				direction = Direction.DESC;
				columnToOrder = "introduced";
			} else if (orderBy.equals("orderByOutroAsc")) {
				direction = Direction.ASC;
				columnToOrder = "discontinued";
			} else if (orderBy.equals("orderByOutroDesc")) {
				direction = Direction.DESC;
				columnToOrder = "discontinued";
			} else if (orderBy.equals("orderByCompanyAsc")) {
				direction = Direction.ASC;
				columnToOrder = "computer.company.name";
			} else if (orderBy.equals("orderByCompanyDesc")) {
				direction = Direction.DESC;
				columnToOrder = "computer.company.name";
			} 
		}
		if(search == null)
		{
			search = "";
		}
		PageRequest page = new PageRequest(pageNumber.intValue(), noOfRecords.intValue(), direction, columnToOrder);
		return new ComputerListWrapper(computerDao.findByNameContaining(search, page), computerDao.countByNameContaining(search));
	}
}