package com.excilys.formation.projet.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDao{
	
	Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	@Autowired
	JdbcTemplate getJdbcTemplate;

	public CompanyDao()
	{
		
	}
	
	public Company find(Long companyid)
	{
		StringBuilder query = new StringBuilder("SELECT * FROM company WHERE id = ?");
		return getJdbcTemplate.queryForObject(query.toString(), new Object[] {companyid}, new CompanyMapper());
	}
	
	public Company find(String companyname)
	{
		StringBuilder query = new StringBuilder("SELECT * FROM company WHERE name = ?");
		return getJdbcTemplate.queryForObject(query.toString(), new Object[] {companyname}, new CompanyMapper());
	}
	
	public List<Company> getAll() 
	{
		StringBuilder query = new StringBuilder("SELECT id, name FROM company; ");
		return getJdbcTemplate.query(query.toString(), new CompanyMapper());
	}
	
	public void addCompany(Company company) throws SQLException {
		
		StringBuilder query = new StringBuilder("INSERT into computer(id,name) VALUES(?,?);");
		getJdbcTemplate.update(query.toString(), new Object[] {company.getId(), company.getName()}, new CompanyMapper());
	}
}
