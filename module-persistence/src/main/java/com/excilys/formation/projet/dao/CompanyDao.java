package com.excilys.formation.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	@SuppressWarnings("unchecked")
	public List<Company> getAll() 
	{
		return entityManager.createQuery("SELECT c FROM Company AS c").getResultList();
	}
}
