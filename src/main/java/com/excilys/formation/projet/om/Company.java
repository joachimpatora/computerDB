package com.excilys.formation.projet.om;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Company {
	String name;
	Long id;
	
	public Company()
	{
		
	}
	
	public Company(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
