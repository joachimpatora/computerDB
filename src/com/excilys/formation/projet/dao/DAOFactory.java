package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

public class DAOFactory {
	
	private static volatile DAOFactory instance = null;
	private ComputerDAO computerdao = null;
	private CompanyDAO companydao = null;
	
	public final static DAOFactory getInstance() {
		if (DAOFactory.instance == null) {
			synchronized(DAOFactory.class) {
				if (DAOFactory.instance == null) {
					DAOFactory.instance = new DAOFactory();
				}
			}
		}
		return DAOFactory.instance;
	}
	
	private DAOFactory()
	{
		this.initDriver();
		this.computerdao = new ComputerDAO(this.getConnection());
		this.companydao = new CompanyDAO(this.getConnection());
	}
	
	public void editComputer(Computer computer)
	{
		computerdao.editComputer(computer);
	}
	
	public Computer getComputer(Long id)
	{
		return computerdao.getComputer(id);
	}
	
	public void addComputer(Computer computer)
	{
		computerdao.insereComputer(computer);
	}
	
	public ArrayList<Computer> getComputer(String computerstr)
	{
		return computerdao.getComputer(computerstr);
	}
	
	public ArrayList<Computer> getComputerList(Long offset, Long nbofrecords)
	{
		return computerdao.getListComputer(offset, nbofrecords);
	}
	
	public int getNbOfComputers()
	{
		return computerdao.getNbOfComputers();
	}
	
	public ArrayList<Company> getCompanyList()
	{
		return companydao.getListCompany();
	}
	
	public ComputerDAO getComputerDAO()
	{
		return computerdao;
	}
	
	public CompanyDAO getCompanyDAO()
	{
		return companydao;
	}
	 
	private void initDriver()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public Connection getConnection()
	{
		Connection conn = null;
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&"+"user=jee-cdb&password=password");
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
}
