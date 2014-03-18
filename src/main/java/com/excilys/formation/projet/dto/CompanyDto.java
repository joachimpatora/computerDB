package com.excilys.formation.projet.dto;

import org.springframework.stereotype.Component;

import com.excilys.formation.projet.om.Company;

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
	
	public Company fromDto(CompanyDto companydto) {
		Company company = new Company();
		company.setId(companydto.getId());
		company.setName(companydto.getName());
		return company;
	}
	
	public CompanyDto toDto(Company company) {
		CompanyDto companydto = new CompanyDto();
		companydto.setId(company.getId());
		companydto.setName(company.getName());
		return companydto;
	}
	
	@Override
	public String toString() {
		return "CompanyDto [name=" + name + ", id=" + id + "]";
	}
}
