package com.excilys.formation.projet.om;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class Computer {
	private Long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinuedDate;
	private Company company;
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public LocalDate getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}
	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}
	
	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	
	
}
