package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MonitorDbDao {

	Logger logger = LoggerFactory.getLogger(MonitorDbDao.class);
	
	@Autowired
	DataSource dataSource;
	
	public MonitorDbDao()
	{
		super();
	}
	
	public void addLog(Long codeError,String message)
	{
		Connection conn = DataSourceUtils.getConnection(dataSource);
		logger.info(conn.toString());
		String query = "INSERT INTO monitorDb (message,error_code) VALUES (?,?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, message);
			stmt.setLong(2, codeError);
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
	}
	
	
}
