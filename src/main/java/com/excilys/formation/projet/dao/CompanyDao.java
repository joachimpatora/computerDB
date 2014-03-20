package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDao {
	
	Logger logger = LoggerFactory.getLogger(CompanyDao.class);
	
	@Autowired
	private MonitorDbDao monitor;

	@Autowired
	DataSource dataSource;
	
	public MonitorDbDao getMonitor() {
		return monitor;
	}

	@Autowired
	public void setMonitor(MonitorDbDao monitor) {
		this.monitor = monitor;
	}

	private CompanyDao()
	{
		
	}
	
	public Company find(Long companyid)
	{
		Connection conn;
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM company WHERE id = ?");
			stmt.setLong(1, companyid);
			rs = stmt.executeQuery();
			while(rs.next()){
				company.setId(companyid);
				company.setName(rs.getString(1));
			}
		} catch (SQLException e1) {
			logger.error("Connection impossible", e1);
		}
		return company;
	}
	
	public Company find(String companyname)
	{
		Connection conn;
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM company WHERE name = ?");
			stmt.setString(1, companyname);
			rs = stmt.executeQuery();
			while(rs.next()){
				company.setId(new Long(rs.getString(1)));
				company.setName(companyname);
			}
		} catch (SQLException e1) {
			logger.error("Connection impossible", e1);
		}
		return company;
	}
	
	public ArrayList<Company> getAll() 
	{

		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		ArrayList<Company> list = new ArrayList<Company>();
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id, name FROM company; ");
			int i = 1;
			while(rs.next())
			{
				Company company = new Company();
				company.setId(new Long(rs.getString(1)));
				company.setName(rs.getString(2));
				i = i + 2;
				list.add(company);
			}

			monitor.addLog(conn, 0L, "Companies transmitted.");
		}
		catch (Exception e)
		{
			logger.error("Erreur lors de la récupération des Companies.", e);
		}
		finally
		{
			try {
				if (rs != null)
				{
					rs.close();
				}
				if (stmt != null)
				{
					stmt.close();
				}
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e) {}
		}
		return list;
	}
	
	public void addCompany(Company company) throws SQLException {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection conn = dataSource.getConnection();
		
		try {
			
			stmt = conn.prepareStatement("INSERT into computer(id,name) VALUES(?,?);");
			
			stmt.setString(1,company.getId().toString());
			stmt.setString(2,company.getName());
			
			stmt.executeUpdate();
			monitor.addLog(conn, 0L, "Company added.");
			
		} catch (Exception e) {
			logger.error("Erreur lors du traitement SQL.", e);
			monitor.addLog(conn, 2L, "Error while adding company.");
		} finally {
			try {
				if (rs != null)
				{	
					rs.close();
				}
				if (stmt != null)
				{
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {}
		}
		
	}
}
