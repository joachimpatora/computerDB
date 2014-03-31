package com.excilys.formation.projet.dto;

import org.springframework.stereotype.Component;

@Component
public class CompanyDto {

	private String name;
	private Long id;

	public CompanyDto() {
		super();
	}

	public CompanyDto(String name, Long id) {
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
	
	@Override
	public String toString() {
		return "CompanyDto [name=" + name + ", id=" + id + "]";
	}
}
