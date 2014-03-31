package com.excilys.formation.projet.dto.mapper;

import com.excilys.formation.projet.dto.CompanyDto;
import com.excilys.formation.projet.om.Company;

public class CompanyDtoMapper {

	public static Company fromDto(CompanyDto companydto)
	{
		Company company = new Company();
		company.setId(companydto.getId());
		company.setName(companydto.getName());
		return company;
	}
	
	public static CompanyDto toDto(Company company)
	{
		CompanyDto companydto = new CompanyDto();
		companydto.setId(company.getId());
		companydto.setName(company.getName());
		return companydto;
	}
}
