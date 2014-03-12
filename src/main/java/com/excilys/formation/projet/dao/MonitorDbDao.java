package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorDbDao {

	Logger logger = LoggerFactory.getLogger(MonitorDbDao.class);
	
	public MonitorDbDao()
	{
		super();
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
