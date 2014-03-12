package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.projet.om.Company;

public class CompanyDAO {
	
	private Connection conn = null;
	
	private CompanyDAO()
	{
		super();
	}
	
	public CompanyDAO(Connection connect)
	{
		this.conn = connect;
	}
	
	public ArrayList<Company> getListCompany()
	{
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
	
	public void insereCompany(Company company) {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement("INSERT into computer(id,name) VALUES(?,?);");
			
			stmt.setString(1,company.getId().toString());
			stmt.setString(2,company.getName());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
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
