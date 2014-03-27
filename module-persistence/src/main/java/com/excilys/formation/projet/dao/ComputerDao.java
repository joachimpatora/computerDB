package com.excilys.formation.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.QCompany;
import com.excilys.formation.projet.om.QComputer;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class ComputerDao {

	@Autowired
	JdbcTemplate getJdbcTemplate;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;
	Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	public ComputerDao() {
		super();
	}

	public List<Computer> getAll(Long offset, Long noOfRecords,
			String searchStr, String orderBy) {
		QComputer computer = QComputer.computer;
		QCompany company= QCompany.company;
		JPAQuery query = new JPAQuery(entityManager);
		
		query.from(computer).leftJoin(computer.company, company);
		if(searchStr != null)
		{
			query.where(computer.name.like("%"+searchStr+"%"));
		}
		if(orderBy != null)
		{
			if (orderBy.equals("orderByNameAsc")) {
				query.orderBy(computer.name.asc());
			} else if (orderBy.equals("orderByNameDesc")) {
				query.orderBy(computer.name.desc());
			} else if (orderBy.equals("orderByIntroAsc")) {
				query.orderBy(computer.introducedDate.asc());
			} else if (orderBy.equals("orderByIntroDesc")) {
				query.orderBy(computer.introducedDate.desc());
			} else if (orderBy.equals("orderByOutroAsc")) {
				query.orderBy(computer.discontinuedDate.asc());
			} else if (orderBy.equals("orderByOutroDesc")) {
				query.orderBy(computer.discontinuedDate.desc());
			} else if (orderBy.equals("orderByCompanyAsc")) {
				query.orderBy(computer.company.name.asc());
			} else if (orderBy.equals("orderByCompanyDesc")) {
				query.orderBy(computer.company.name.desc());
			} else {
				query.orderBy(computer.id.asc());
			}
		}
		return query.limit(noOfRecords).offset(offset).list(computer);
	}

	public int getNbOfComputers(String search) {
		QComputer computer = QComputer.computer;
		JPAQuery query = new JPAQuery(entityManager);
		query.from(computer);		
		if (search != null) {
			query.where(computer.name.like("%"+search+"%"));
		}
		return (int) query.count();
	}

	public Computer get(Long id) {
		return entityManager.find(Computer.class, id);
	}

	public void delete(Long id) {
		entityManager.remove(entityManager.find(Computer.class, id));
	}

	public void update(Computer computer) {
		java.sql.Date introduced = null;
		java.sql.Date discontinued = null;

		if (computer.getIntroducedDate() != null) {
			introduced = new java.sql.Date(computer.getIntroducedDate()
					.toDate().getTime());
		}
		if (computer.getDiscontinuedDate() != null) {
			discontinued = new java.sql.Date(computer.getDiscontinuedDate()
					.toDate().getTime());
		}
		entityManager.merge(computer);
	}

	public void add(Computer computer) {
		java.sql.Date introduced = null;
		java.sql.Date discontinued = null;
		if (computer.getIntroducedDate() != null) {
			introduced = new java.sql.Date(computer.getIntroducedDate()
					.toDate().getTime());
		}
		if (computer.getDiscontinuedDate() != null) {
			discontinued = new java.sql.Date(computer.getDiscontinuedDate()
					.toDate().getTime());
		}

		entityManager.persist(computer);
	}
}
