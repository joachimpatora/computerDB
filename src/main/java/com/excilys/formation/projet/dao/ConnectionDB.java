package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Repository
public class ConnectionDB {
	
	private static BoneCP connectionPool = null;
	
	public static void Initialize() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
		config.setUsername("jee-cdb");
		config.setPassword("password");
		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);
		connectionPool = new BoneCP(config); 
	}
			
	public static Connection getConnection() throws SQLException{
		if(connectionPool == null){
			Initialize();
		}
		return connectionPool.getConnection();
	}
}

