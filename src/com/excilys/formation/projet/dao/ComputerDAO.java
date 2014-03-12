package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.projet.om.*;

public class ComputerDAO {
	
	private Connection conn = null;
	private int nbofcomputers = 0;
	
	private ComputerDAO()
	{
		super();
	}
	
	public ComputerDAO(Connection connect)
	{
		this.conn = connect;
	}
	
	public ArrayList<Computer> getListComputer(Long offset, Long noOfRecords)
	{
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try
		{
			stmt = conn.prepareStatement("SELECT SQL_CALC_FOUND_ROWS computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id LIMIT ?, ? ; ");
			stmt.setLong(1,offset);
			stmt.setLong(2,noOfRecords);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Computer computers = new Computer();
				computers.setId(new Long(rs.getString(1)));
				computers.setName(rs.getString(2));
				computers.setIntroducedDate(rs.getString(3));
				computers.setDiscontinuedDate(rs.getString(4));
				computers.setCompany(rs.getString(5));
				
				list.add(computers);
			}
			try {
				if (rs != null)
				{
					rs.close();
				}
			}
			catch(Exception e){}
			rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next())
            {
            	this.nbofcomputers = rs.getInt(1);
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
	
	public int getNbOfComputers()
	{
		return this.nbofcomputers;
	}
	
	public Computer getComputer(Long id)
	{
		Computer computer = new Computer();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(id != null)
		{
			try
			{
				stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.id=?; ");
				stmt.setLong(1,id);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					computer.setId(new Long(rs.getString(1)));
					computer.setName(rs.getString(2));
					computer.setIntroducedDate(rs.getString(3));
					computer.setDiscontinuedDate(rs.getString(4));
					computer.setCompany(rs.getString(5));
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
		}
		return computer;
	}
	
	public ArrayList<Computer> getComputer(String computerstr){
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(computerstr != null)
		{
			try
			{
				computerstr = "%"+computerstr+"%";
				System.out.println(computerstr);
				stmt = conn.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ?; ");
				stmt.setString(1,computerstr);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					Computer computers = new Computer();
					computers.setId(new Long(rs.getString(1)));
					computers.setName(rs.getString(2));
					computers.setIntroducedDate(rs.getString(3));
					computers.setDiscontinuedDate(rs.getString(4));
					computers.setCompany(rs.getString(5));
					
					list.add(computers);
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
		}
		return list;
	}
	
	public void editComputer(Computer computer){
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Long companyid = null;
		if((computer.getCompany() != null)&&((new Long(computer.getCompany())) != 0))
		{
			companyid = new Long(computer.getCompany());
		}
		
		try{
			stmt = conn.prepareStatement("UPDATE computer SET name = ?, introduced = ? ,discontinued = ?, company_id = ? WHERE computer.id = ?");
			stmt.setString(1,computer.getName());
			
			if ((computer.getIntroducedDate() == null) || (computer.getIntroducedDate().equals("")))
			{
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else 
			{
				stmt.setString(2,computer.getIntroducedDate());
			}
			if ((computer.getDiscontinuedDate() == null) || (computer.getDiscontinuedDate().equals("")))
			{
				stmt.setNull(3,java.sql.Types.DATE);
			}
			else 
			{
				stmt.setString(3,computer.getDiscontinuedDate());
			}
			if(companyid != null)
			{
				stmt.setString(4,companyid.toString());
			}
			else
			{
				stmt.setNull(4,java.sql.Types.NULL);
			}
			stmt.setLong(5, computer.getId());
			
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
	
	public void insereComputer(Computer computer) {
		
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Long companyid = null;
		if((computer.getCompany() != null)&&((new Long(computer.getCompany())) != 0))
		{
			companyid = new Long(computer.getCompany());
		}
		
		try{
			stmt = conn.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);");
			
			stmt.setString(1,computer.getName());
			if ((computer.getIntroducedDate() == null) || (computer.getIntroducedDate().equals("")))
			{
				stmt.setNull(2, java.sql.Types.DATE);
			}
			else 
			{
				stmt.setString(2,computer.getIntroducedDate());
			}
			if ((computer.getDiscontinuedDate() == null) || (computer.getDiscontinuedDate().equals("")))
			{
				stmt.setNull(3,java.sql.Types.DATE);
			}
			else 
			{
				stmt.setString(3,computer.getDiscontinuedDate());
			}
			if(companyid != null)
			{
				stmt.setString(4,companyid.toString());
			}
			else
			{
				stmt.setNull(4,java.sql.Types.NULL);
			}
			
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
