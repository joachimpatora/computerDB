package com.excilys.formation.projet.dto.mapper;

import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.formation.projet.dto.ComputerDto;
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

@Component
public class ComputerDtoMapper implements MessageSourceAware {
	
	private static MessageSource messageSource;
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		ComputerDtoMapper.messageSource = messageSource;
	}

	public static Computer fromDto(ComputerDto computerdto) {
		Computer computer = new Computer();
		computer.setId(computerdto.getId());
		computer.setName(computerdto.getName());
		Locale locale = LocaleContextHolder.getLocale();
		String dateFormat = messageSource.getMessage("date.format", null,
				locale);
		DateTimeFormatter dateStringFormat = DateTimeFormat
				.forPattern(dateFormat);

		computer.setIntroducedDate(LocalDate.parse(computerdto.getIntroducedDate(),
				dateStringFormat));
		computer.setDiscontinuedDate(LocalDate.parse(
				computerdto.getDiscontinuedDate(), dateStringFormat));

		Company company = new Company();
		company.setId(computerdto.getCompanyid());
		company.setName(computerdto.getCompanyname());
		computer.setCompany(company);

		return computer;
	}
	
	public static ComputerDto toDto(Computer computer) {
		Locale locale = LocaleContextHolder.getLocale();
		String dateFormat = messageSource.getMessage("date.format", null,
				locale);

		ComputerDto computerdto = new ComputerDto();
		computerdto.setId(computer.getId());
		computerdto.setName(computer.getName());

		if(computer.getIntroducedDate()!= null)
		{
			computerdto.setIntroducedDate(computer.getIntroducedDate().toString(
				dateFormat, locale));
		}
		else
		{
			computerdto.setIntroducedDate(null);
		}
		if(computer.getDiscontinuedDate() != null)
		{
			computerdto.setDiscontinuedDate(computer.getDiscontinuedDate()
				.toString(dateFormat, locale));
		}
		else
		{
			computerdto.setDiscontinuedDate(null);
		}
		if (computer.getCompany() != null) {
			computerdto.setCompanyid(computer.getCompany().getId());
			computerdto.setCompanyname(computer.getCompany().getName());
		}
		else {
			computerdto.setCompanyid(null);
			computerdto.setCompanyname(null);
		}
		return computerdto;
	}
}
