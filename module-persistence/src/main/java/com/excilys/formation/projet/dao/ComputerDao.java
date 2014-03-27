package com.excilys.formation.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;
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

	public List<Computer> getAll(Long offset, Long noOfRecords,
			String searchStr, String orderBy) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		Join<Computer, Company> company = computerRoot.join("company",
				JoinType.LEFT);
		criteria.select(computerRoot);

		if (searchStr != null) {
			String search = new StringBuilder("%").append(searchStr)
					.append("%").toString();
			criteria.where(builder.like(
					computerRoot.get("name").as(String.class), search));
		}
		if (orderBy != null) {
			if (orderBy.equals("orderByNameAsc")) {
				criteria.orderBy(builder.asc(computerRoot.get("name")));
			} else if (orderBy.equals("orderByNameDesc")) {
				criteria.orderBy(builder.desc(computerRoot.get("name")));
			} else if (orderBy.equals("orderByIntroAsc")) {
				criteria.orderBy(builder.asc(computerRoot.get("introduced")));
			} else if (orderBy.equals("orderByIntroDesc")) {
				criteria.orderBy(builder.desc(computerRoot.get("introduced")));
			} else if (orderBy.equals("orderByOutroAsc")) {
				criteria.orderBy(builder.asc(computerRoot.get("discontinued")));
			} else if (orderBy.equals("orderByOutroDesc")) {
				criteria.orderBy(builder.desc(computerRoot.get("discontinued")));
			} else if (orderBy.equals("orderByCompanyAsc")) {
				criteria.orderBy(builder.asc(company.get("id")));
			} else if (orderBy.equals("orderByCompanyDesc")) {
				criteria.orderBy(builder.desc(company.get("id")));
			} else {
				criteria.orderBy(builder.asc(computerRoot.get("id")));
			}
		}
		return (List<Computer>) entityManager.createQuery(criteria)
				.setFirstResult(offset.intValue())
				.setMaxResults(noOfRecords.intValue()).getResultList();
	}

	public int getNbOfComputers(String search) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Computer> computerRoot = criteria.from(Computer.class);
		criteria.select(builder.count(computerRoot));
		computerRoot.join("company", JoinType.LEFT);
		if(search != null)
		{
			String searchStr = new StringBuilder("%").append(search)
					.append("%").toString();
			criteria.where(builder.like(
					computerRoot.get("name").as(String.class), searchStr));
		}
		
		return entityManager.createQuery(criteria).getSingleResult().intValue();
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
