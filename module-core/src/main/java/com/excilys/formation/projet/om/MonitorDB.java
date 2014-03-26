package com.excilys.formation.projet.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitorDb")
public class MonitorDB {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="message")
	String messageLog;
	@Column(name="error_code")
	Long error_code;

	public Long getError_code() {
		return error_code;
	}

	public void setError_code(Long error_code) {
		this.error_code = error_code;
	}
	
	public String getMessageLog() {
		return messageLog;
	}

	public void setMessageLog(String messageLog) {
		this.messageLog = messageLog;
	}
}
