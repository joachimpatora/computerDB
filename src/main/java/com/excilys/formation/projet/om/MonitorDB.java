package com.excilys.formation.projet.om;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitorDB {
	
	String messageLog = "";
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
