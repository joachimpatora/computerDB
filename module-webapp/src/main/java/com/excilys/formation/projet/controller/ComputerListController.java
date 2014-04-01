package com.excilys.formation.projet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.projet.dto.CompanyDto;
import com.excilys.formation.projet.dto.ComputerDto;
import com.excilys.formation.projet.dto.mapper.CompanyDtoMapper;
import com.excilys.formation.projet.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.om.ComputerListWrapper;
import com.excilys.formation.projet.service.CompanyService;
import com.excilys.formation.projet.service.ComputerService;

@Controller
@RequestMapping("/Complist")
public class ComputerListController {

	final Logger logger = LoggerFactory.getLogger(ComputerListController.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	private final Long RECORDS_PER_PAGE = 15L;

	public ComputerService getComputerService() {
		return computerService;
	}

	public ComputerListController() {
		super();
	}

	@RequestMapping(value = "/dashboard", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView listOfComputers(
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long pageNb,
			@RequestParam(value = "search", required = false) String searchStr,
			@RequestParam(value = "main", required = false) String main,
			@RequestParam(value = "message", required = false) String message) {
		
		Map<String, String> queryParameters = new HashMap<>();
		Long recordsPerPage = RECORDS_PER_PAGE;
		List<ComputerDto> listdto = new ArrayList<ComputerDto>();
		ComputerListWrapper clw;

		logger.info("Displaying dashboard");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dashboard");
		logger.error("Ordinateur recherché", searchStr);
		
		if(orderBy != null)
		{
			queryParameters.put("orderBy", orderBy);
		}
		if(searchStr != null)
		{
			queryParameters.put("search", searchStr);
		}

		clw = computerService.findAllByName((pageNb - 1) * recordsPerPage,
				recordsPerPage, searchStr, orderBy);
		for (Object comp : clw.getComputerList()) {
			listdto.add(ComputerDtoMapper.toDto((Computer) comp));
		}
		modelAndView.addObject("listOfComputers", listdto);

		int noOfRecords = clw.getCount();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		modelAndView.addObject("queryParameters", queryParameters);
		modelAndView.addObject("totalNbOfComp", noOfRecords);
		modelAndView.addObject("orderBy", orderBy);
		modelAndView.addObject("noOfPages", noOfPages);
		modelAndView.addObject("currentPage", pageNb);
		modelAndView.addObject("message", message);
		modelAndView.addObject("search", searchStr);
		return modelAndView;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public ModelAndView addComputer(Model m) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addComputer");
		try {
			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				listdto.add(CompanyDtoMapper.toDto(company));
			}
			m.addAttribute("ComputerDto", new ComputerDto());
			modelAndView.addObject("listOfCompanies", listdto);
		} catch (Exception e) {
		}
		return modelAndView;
	}

	@RequestMapping(value = "/editComputer", method = RequestMethod.GET)
	public ModelAndView editComputer(Model m,
			@RequestParam(value = "compId", required = true) Long compId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("editComputer");
		try {
			Computer computer = computerService.get(compId);
			ComputerDto computerdto = new ComputerDto();
			computerdto = ComputerDtoMapper.toDto(computer);
			modelAndView.addObject("computer", computerdto);

			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				listdto.add(CompanyDtoMapper.toDto(company));
			}
			m.addAttribute("ComputerDto", computerdto);
			modelAndView.addObject("listOfCompanies", listdto);
		} catch (Exception e) {
			logger.error("Mauvaise récupération de l'ordinateur à éditer", e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/addComputerResult", method = RequestMethod.POST)
	public ModelAndView addComputer(
			@ModelAttribute("ComputerDto") @Valid ComputerDto computerdto,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();

		if (!result.hasErrors()) {
			logger.info("Displaying dashboard");
			Computer computer = new Computer();
			computer = ComputerDtoMapper.fromDto(computerdto);
			try {
				computerService.create(computer);
			} catch (Exception e) {
				logger.error("Mauvais Update", e);
			}
			modelAndView.addObject("message", "Ajout effectué");
			modelAndView.setViewName("redirect:dashboard");
		} else {
			modelAndView.setViewName("addComputer");
			modelAndView.addObject("ComputerDto", computerdto);
			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				listdto.add(CompanyDtoMapper.toDto(company));
			}
			modelAndView.addObject("listOfCompanies", listdto);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/updateComputer", method = RequestMethod.POST)
	public ModelAndView updateComputer(
			@RequestParam(value = "hiddenid", required = true) Long hiddenid,
			@RequestParam(value = "updateButton", required = true) String updateButton,
			@ModelAttribute("ComputerDto") @Valid ComputerDto computerdto,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:dashboard");
		if (("Edit".equals(updateButton))
				|| ("Edition".equals(updateButton))) {
			if (!result.hasErrors()) {
				logger.info("Displaying dashboard");
				Computer computer = new Computer();
				computer = ComputerDtoMapper.fromDto(computerdto);
				computer.setId(hiddenid);
				computerService.update(computer);
				modelAndView.addObject("message", "Modification effectuée");
			} else {
				modelAndView.setViewName("editComputer");
				modelAndView.addObject("computer", computerdto);
				List<Company> liste = companyService.getAll();
				List<CompanyDto> listdto = new ArrayList<CompanyDto>();
				for (Company company : liste) {
					listdto.add(CompanyDtoMapper.toDto(company));
				}
				modelAndView.addObject("listOfCompanies", listdto);
			}
		} else {
			try {
				computerService.delete(hiddenid);
			} catch (Exception e) {
				logger.error("Delete raté", e);
			}
			modelAndView.addObject("message", "Suppression effectuée");
		}
		
		return modelAndView;
	}
}
