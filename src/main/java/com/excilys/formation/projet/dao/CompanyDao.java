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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDao {
	
	Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	@Autowired
	DataSource dataSource;

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
			conn = DataSourceUtils.getConnection(dataSource);
			stmt = conn.prepareStatement("SELECT * FROM company WHERE id = ?");
			stmt.setLong(1, companyid);
			rs = stmt.executeQuery();
			while(rs.next()){
				company.setId(companyid);
				company.setName(rs.getString(1));
			}
		} catch (SQLException e1) {
			logger.error("Connection impossible", e1);
		} finally
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
			} catch (SQLException e) {}
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
			conn = DataSourceUtils.getConnection(dataSource);
			stmt = conn.prepareStatement("SELECT * FROM company WHERE name = ?");
			stmt.setString(1, companyname);
			rs = stmt.executeQuery();
			while(rs.next()){
				company.setId(new Long(rs.getString(1)));
				company.setName(companyname);
			}
		} catch (SQLException e1) {
			logger.error("Connection impossible", e1);
		} finally
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
			} catch (SQLException e) {}
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
			conn = DataSourceUtils.getConnection(dataSource);
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
			} catch (SQLException e) {}
		}
		return list;
	}
	
	public void addCompany(Company company) throws SQLException {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection conn = DataSourceUtils.getConnection(dataSource);
		
		try {
			
			stmt = conn.prepareStatement("INSERT into computer(id,name) VALUES(?,?);");
			
			stmt.setString(1,company.getId().toString());
			stmt.setString(2,company.getName());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			logger.error("Erreur lors du traitement SQL.", e);
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
			} catch (SQLException e) {}
		}
		
	}
}
