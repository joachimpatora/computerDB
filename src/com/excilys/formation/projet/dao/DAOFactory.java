package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {

	private static volatile DAOFactory instance = null;
	private ComputerDao computerdao = null;
	private CompanyDao companydao = null;

	public final static DAOFactory getInstance() {
		if (DAOFactory.instance == null) {
			synchronized (DAOFactory.class) {
				if (DAOFactory.instance == null) {
					DAOFactory.instance = new DAOFactory();
				}
			}
		}
		return DAOFactory.instance;
	}

	private DAOFactory() {
		this.initDriver();
		this.computerdao = new ComputerDao(this.getConnection());
		this.companydao = new CompanyDao(this.getConnection());
	}

	public ComputerDao getComputerDAO() {
		return computerdao;
	}

	public CompanyDao getCompanyDAO() {
		return companydao;
	}

	private void initDriver() {
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

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&"
							+ "user=jee-cdb&password=password");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
}
