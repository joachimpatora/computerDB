package com.excilys.formation.projet.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Computer;

@Repository	
public class ComputerDao {

	final private Long ASC = 0L;
	final private Long DESC = 1L;
	
	@Autowired
	JdbcTemplate getJdbcTemplate;
	
	Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	Long old_offset = 0L;
	Long orderByDir = ASC;
	String old_orderBy = "";
	String OrderByDirection = "ASC";
		
	public ComputerDao() {
		super();
	}

	public List<Computer> getAll(Long offset, Long noOfRecords,
			String searchStr, String orderBy) {
		
		StringBuilder searchSb = new StringBuilder();
		StringBuilder query = new StringBuilder("SELECT computer.id, computer.name, introduced, discontinued, computer.company_id, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id");

		if(searchStr != null)
		{
			query.append(" WHERE computer.name LIKE ? ");
		}
		if (orderBy != null)
		{
			query = getOrder(orderBy, query);
		}
		query.append(" LIMIT ?, ?;");
		if(searchStr != null)
		{
			searchSb.append("%").append(searchStr).append("%");
			return getJdbcTemplate.query(query.toString(), new Object[]{searchSb.toString(), offset, noOfRecords}, new ComputerMapper());
		}
		return getJdbcTemplate.query(query.toString(), new Object[]{offset, noOfRecords}, new ComputerMapper());
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
			query.append(" ORDER BY ").append("computer.company_id ").append(" ASC");
		} else if (orderBy.equals("orderByCompanyDesc")) {
			query.append(" ORDER BY ").append("computer.company_id ").append(" DESC");
		} else {
			query.append(" ORDER BY ").append("computer.id ").append(" ASC");
		}
		return query;
	}
	
	public int getNbOfComputers(String search) {
		StringBuilder query = new StringBuilder("SELECT count(*) FROM computer");
		if(search == null)
		{
			return getJdbcTemplate.queryForObject(query.toString(), Integer.class);
		}
		else {
			query.append(" WHERE name LIKE ?");
			return getJdbcTemplate.queryForObject(query.toString(), Integer.class, "%" + search + "%");
		}
	}

	public Computer get(Long id){
		String query = "SELECT computer.id, computer.name, introduced, discontinued, computer.company_id, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.id=?; ";
		return  getJdbcTemplate.queryForObject(query, new Object[] {id}, new ComputerMapper());
	}

	public void delete(Long id) {
		String query = "DELETE FROM computer WHERE computer.id = ?";
		getJdbcTemplate.update(query, new Object[] {id});
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
		
		String query = "UPDATE computer SET name = ?, introduced = ? ,discontinued = ?, company_id = ? WHERE computer.id = ?";
		getJdbcTemplate.update(query, new Object[] {computer.getName(), introduced, discontinued, computer.getCompany().getId(), computer.getId()});

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
		
		String query = "INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
		
		getJdbcTemplate.update(query, new Object[] {computer.getName(), introduced, discontinued, computer.getCompany().getId()});
	}
}
