package com.excilys.formation.projet.om;

import org.springframework.stereotype.Component;

@Component
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
