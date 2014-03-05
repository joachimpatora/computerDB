package com.excilys.formation.projet.om;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Computer {
	Long id;
	String name;
	String companyname;
	String introducedDate;
	String discontinuedDate;
	
	public Computer()
	{
		super();
	}
	
	public Computer(Long id, String name) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return companyname;
	}
	public void setCompany(String companyname) {
		this.companyname = companyname;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	
	
}
