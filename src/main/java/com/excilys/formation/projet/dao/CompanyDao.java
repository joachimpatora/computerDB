package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.projet.dao.ConnectionDB;
import com.excilys.formation.projet.om.Company;

@Repository
public class CompanyDao {
	
	Logger logger = LoggerFactory.getLogger(CompanyDao.class);
	
	@Autowired
	private MonitorDbDao monitor;
	
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
	
	public ArrayList<Company> getAll() throws SQLException
	{
		Connection conn = ConnectionDB.getConnection();
		ArrayList<Company> list = new ArrayList<Company>();
		ResultSet rs = null;
		Statement stmt = null;
		
		try
		{
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
			monitor.addLog(conn, 2L, "Error while retrieving companies.");
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
				conn.close();
			} catch (SQLException e) {}
		}
		return list;
	}
	
	public void addCompany(Company company) throws SQLException {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection conn = ConnectionDB.getConnection();
		
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
