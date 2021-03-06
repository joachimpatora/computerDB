package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.projet.om.*;

public class ComputerDao {
	
	final private Long ASC = 0L;
	final private Long DESC = 1L;
	private static ComputerDao _instance = null;
	
	Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	private int nbofcomputers = 0;
	private String searchCache = "";
	Long old_offset = 0L;
	Long orderByDir = ASC;
	String old_orderBy = "";
	String OrderByDirection = "ASC";
	
	private ComputerDao()
	{
		
	}
	
	synchronized public static ComputerDao getInstance() {
		if (_instance == null) {
			_instance = new ComputerDao();
		}
		return _instance;
	}
	
	public ArrayList<Computer> getAll(Long offset, Long noOfRecords, String searchStr, String orderBy) throws SQLException
	{
		Connection conn = ConnectionDB.getConnection();
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		if(searchStr == null)
		{
			searchStr = "";
			searchCache = "";
		}
		else if((searchStr != null)&&(!searchStr.equals("")))
		{
			if(offset!=0)
			{
				old_offset = offset;
				if(!searchCache.equals(""))
				{
					searchStr = searchCache;
				}
			}
			else if((old_offset != 0)&&(offset==0))
			{
				if(!searchCache.equals(""))
				{
					searchStr = searchCache;
				}
			}
		}
		else if(!searchCache.equals(""))
		{
			searchStr = searchCache;
		}
		else 
		{
			searchCache = "";
			old_offset = 0L;
		}
		try
		{
			String sqlFormat;
			final String SQL = "SELECT SQL_CALC_FOUND_ROWS computer.id, computer.name, introduced, discontinued, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ? ORDER BY %s %s LIMIT ?, ? ; ";
			
			if(orderBy != null)
			{
				if(orderBy.equals(""))
				{
					 orderBy = old_orderBy;
					 orderByDir = Math.abs(orderByDir -1L);
				}
				else
				{
					if(orderByDir == ASC)
					{
						orderByDir = DESC;
						OrderByDirection = "ASC";
					}
					else
					{
						orderByDir = ASC;
						OrderByDirection = "DESC";
					}
				}
				if(orderBy.equals("Name"))
				{
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.name", OrderByDirection);
				}
				else if(orderBy.equals("IntroDate"))
				{
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.introduced", OrderByDirection);
				}
				else if(orderBy.equals("OutroDate"))
				{
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.discontinued", OrderByDirection);
				}
				else if(orderBy.equals("Company"))
				{
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.company_id", OrderByDirection);
				}
				else
				{
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.id", OrderByDirection);
				}
			}
			else
			{
				old_orderBy = "";
				sqlFormat = String.format(SQL, "computer.id", OrderByDirection);
			}
			stmt = conn.prepareStatement(sqlFormat);
			if(searchStr!=null)
			{
				
				searchStr = "%"+searchStr+"%";
				searchCache = searchStr;
				stmt.setString(1,searchStr);
			}
			else
			{
				stmt.setString(1,"%"+"%");
			}
			
			stmt.setLong(2,offset);
			stmt.setLong(3,noOfRecords);
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
				conn.close();
			} catch (SQLException e) {}
		}
		return list;
	}
	
	public int getNbOfComputers()
	{
		return this.nbofcomputers;
	}
	
	public Computer get(Long id) throws SQLException
	{
		Connection conn = ConnectionDB.getConnection();
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
				logger.error("Erreur lors du traitement SQL.", e);
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
		}
		return computer;
	}
	
	public void delete(Long id) throws SQLException
	{
		Connection conn = ConnectionDB.getConnection();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		if(id == null)
			return;
		
		try{
			stmt = conn.prepareStatement("DELETE FROM computer WHERE computer.id = ?");
			stmt.setLong(1,id);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			logger.error("Erreur lors du traitement SQL de suppression.", e);
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
	
	public void update(Computer computer) throws SQLException{
		Connection conn = ConnectionDB.getConnection();
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
				conn.close();
			} catch (SQLException e) {}
		}
	}
	
	public void add(Computer computer) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
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
				conn.close();
			} catch (SQLException e) {}
		}
		
	}
}
