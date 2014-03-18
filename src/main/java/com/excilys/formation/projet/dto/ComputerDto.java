package com.excilys.formation.projet.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

@Component
public class ComputerDto {
	
	private Long id;
	@NotNull
	private String name;
	@NotNull
	@Pattern(regexp="^(((19|20)[0-9][0-9])[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")
	private String introducedDate;
	@NotNull
	@Pattern(regexp="^(((19|20)[0-9][0-9])[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")
	private String discontinuedDate;
	private String companyname;
	private Long companyid;
	
	public ComputerDto(Long id, String name, String introducedDate,
			String discontinuedDate, String companyname, Long companyid) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.companyname = companyname;
		this.companyid = companyid;
	}
	
	public ComputerDto() {
		super();
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
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	public Computer fromDto(ComputerDto computerdto)
	{
		Computer computer = new Computer();
		computer.setId(computerdto.getId());
		computer.setName(computerdto.getName());
		
		computer.setIntroducedDate(toLocalDateParser(computerdto.introducedDate));
		computer.setDiscontinuedDate(toLocalDateParser(computerdto.getIntroducedDate()));
		
		Company company = new Company();
		company.setId(computerdto.getCompanyid());
		company.setName(computerdto.getCompanyname());
		computer.setCompany(company);
		
		return computer;
	}
	
	public ComputerDto toDto(Computer computer)
	{
		ComputerDto computerdto = new ComputerDto();
		computerdto.setId(computer.getId());
		computerdto.setName(computer.getName());
		computerdto.setIntroducedDate(computer.getIntroducedDate().toString());
		computerdto.setDiscontinuedDate(computer.getDiscontinuedDate().toString());
		computerdto.setCompanyid(computer.getCompany().getId());
		computerdto.setCompanyname(computer.getCompany().getName());
		return computerdto;
	}
	
	private LocalDate toLocalDateParser(String date)
	{
		return new LocalDate(date);
	}
	
}
