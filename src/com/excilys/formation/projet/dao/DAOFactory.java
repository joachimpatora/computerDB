package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {
	
	Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private static volatile DAOFactory instance = null;
	private ComputerDao computerdao = null;
	private CompanyDao companydao = null;
	BoneCP connectionPool;

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
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
			config.setUsername("jee-cdb");
			config.setPassword("password");	
			connectionPool = new BoneCP(config); 
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection connection;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
