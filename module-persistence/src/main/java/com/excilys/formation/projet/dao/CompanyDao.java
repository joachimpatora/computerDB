package com.excilys.formation.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;

@Repository
@Transactional
public class CompanyDao{
	
	Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	@Autowired
	JdbcTemplate getJdbcTemplate;
	
	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public CompanyDao()
	{
		
	}
	
	public List<Company> getAll() 
	{
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> companyRoot = criteria.from(Company.class);
		criteria.select(companyRoot);
		return entityManager.createQuery(criteria).getResultList();
	}
}
