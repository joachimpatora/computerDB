package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorDbDao {

	private static MonitorDbDao _instance = null;
	
	Logger logger = LoggerFactory.getLogger(MonitorDbDao.class);
	
	private MonitorDbDao()
	{
	}
	
	synchronized public static MonitorDbDao getInstance(){
		if(_instance == null) {
			_instance = new MonitorDbDao();
		}
		return _instance;
	}
	
	public void addLog(Connection connect, Long codeError,String message)
	{
		String query = "INSERT INTO monitorDb (message,error_code) VALUES (?,?)";
		try (PreparedStatement stmt = connect.prepareStatement(query)) {
			stmt.setString(1, message);
			stmt.setLong(2, codeError);
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
	}
	
	
}
