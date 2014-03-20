package com.excilys.formation.projet.dto;

import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

@Component
public class ComputerDto implements MessageSourceAware {
	
	private Long id;
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	@DateFormat
	private String introducedDate;
	@NotNull
	@NotEmpty
	@DateFormat
	private String discontinuedDate;
	private String companyname;
	private Long companyid;
	
	private static MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource) {
		ComputerDto.messageSource = messageSource;	
		}
	
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
		Locale locale = LocaleContextHolder.getLocale();
		String dateFormat = messageSource.getMessage("date.format", null, locale);
		DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern(dateFormat);
		
		computer.setIntroducedDate(LocalDate.parse(computerdto.introducedDate, dateStringFormat));
		computer.setDiscontinuedDate(LocalDate.parse(computerdto.discontinuedDate, dateStringFormat));
		
		Company company = new Company();
		company.setId(computerdto.getCompanyid());
		company.setName(computerdto.getCompanyname());
		computer.setCompany(company);
		
		return computer;
	}
	
	public ComputerDto toDto(Computer computer)
	{
		Locale locale = LocaleContextHolder.getLocale();
		String dateFormat = messageSource.getMessage("date.format", null, locale);
		
		ComputerDto computerdto = new ComputerDto();
		computerdto.setId(computer.getId());
		computerdto.setName(computer.getName());
		computerdto.setIntroducedDate(computer.getIntroducedDate().toString(dateFormat, locale));
		computerdto.setDiscontinuedDate(computer.getDiscontinuedDate().toString(dateFormat, locale));
		computerdto.setCompanyid(computer.getCompany().getId());
		computerdto.setCompanyname(computer.getCompany().getName());
		return computerdto;
	}
}
