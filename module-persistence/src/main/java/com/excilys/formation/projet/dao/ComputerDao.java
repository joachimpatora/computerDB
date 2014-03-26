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

	@SuppressWarnings("unchecked")
	public List<Computer> getAll(Long offset, Long noOfRecords,
			String searchStr, String orderBy) {
		StringBuilder query = new StringBuilder("SELECT computer FROM Computer AS computer LEFT JOIN computer.company company");

		if(searchStr != null)
		{
			query.append(" WHERE computer.name LIKE :search");
		}
		if (orderBy != null)
		{
			query = getOrder(orderBy, query);
		}
		Query hQuery = entityManager.createQuery(query.toString());
		if(searchStr != null)
		{
			hQuery.setParameter("search", new StringBuilder("%").append(searchStr).append("%").toString());
		}
		hQuery.setFirstResult(offset.intValue());
		hQuery.setMaxResults(noOfRecords.intValue());
		return hQuery.getResultList();
	}
		
	private StringBuilder getOrder(String orderBy, StringBuilder query)
	{
		if (orderBy.equals("orderByNameAsc")) {
			query.append(" ORDER BY ").append("computer.name ").append(" ASC");
		} else if (orderBy.equals("orderByNameDesc")){
			query.append(" ORDER BY ").append("computer.name ").append(" DESC");
		} else if (orderBy.equals("orderByIntroAsc")) {
			query.append(" ORDER BY ").append("introduced ").append(" ASC");
		} else if (orderBy.equals("orderByIntroDesc")) {
			query.append(" ORDER BY ").append("introduced ").append(" DESC");
		} else if (orderBy.equals("orderByOutroAsc")) {
			query.append(" ORDER BY ").append("discontinued ").append(" ASC");
		} else if (orderBy.equals("orderByOutroDesc")) {
			query.append(" ORDER BY ").append("discontinued ").append(" DESC");
		} else if (orderBy.equals("orderByCompanyAsc")) {
			query.append(" ORDER BY ").append("company_id ").append(" ASC");
		} else if (orderBy.equals("orderByCompanyDesc")) {
			query.append(" ORDER BY ").append("company_id ").append(" DESC");
		} else {
			query.append(" ORDER BY ").append("computer.id ").append(" ASC");
		}
		return query;
	}
	
	public int getNbOfComputers(String search) {
		StringBuilder query = new StringBuilder("SELECT count(*) FROM Computer");
		if(search != null)
		{
			query.append(" WHERE name LIKE :search");
		}
		Query hQuery = entityManager.createQuery(query.toString());
		if(search != null)
		{
			hQuery.setParameter("search", new StringBuilder("%").append(search).append("%").toString());
		}
		return Integer.parseInt(hQuery.getSingleResult().toString());
	}

	public Computer get(Long id){
		return entityManager.find(Computer.class, id);
	}

	public void delete(Long id) {
		entityManager.remove(entityManager.find(Computer.class, id));
	}

	public void update(Computer computer) {
		java.sql.Date introduced = null;
		java.sql.Date discontinued = null;
		
		if (computer.getIntroducedDate()!=null) {
			introduced = new java.sql.Date(computer
				.getIntroducedDate().toDate().getTime());
		}
		if(computer.getDiscontinuedDate() != null) {
			discontinued = new java.sql.Date(computer
				.getDiscontinuedDate().toDate().getTime());
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
