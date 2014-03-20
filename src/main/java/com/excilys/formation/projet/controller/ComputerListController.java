package com.excilys.formation.projet.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.CompanyService;
import com.excilys.formation.projet.service.ComputerService;

@Controller
@RequestMapping("/Complist")
public class ComputerListController {

	final Logger logger = LoggerFactory.getLogger(ComputerListController.class);

	private ComputerService computerService;
	private CompanyService companyService;

	private final Long RECORDS_PER_PAGE = 15L;

	public ComputerService getComputerService() {
		return computerService;
	}

	@Autowired
	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ComputerListController() {
		super();
	}

	@RequestMapping(value = "/dashboard", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView listOfComputers(
			@RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
			@RequestParam(value = "page", required = false, defaultValue = "1") Long pageNb,
			@RequestParam(value = "search", required = false) String searchStr,
			@RequestParam(value = "main", required = false) String main,
			@RequestParam(value = "message", required = false) String message) {

		Long recordsPerPage = RECORDS_PER_PAGE;
		List<Computer> liste;
		List<ComputerDto> listdto = new ArrayList<ComputerDto>();

		logger.info("Displaying dashboard");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dashboard");
		logger.error("Ordinateur recherché", searchStr);

		try {
			if ((main != null) && (main.equals("accueil"))) {
				liste = computerService.getAll((pageNb - 1) * recordsPerPage,
						recordsPerPage, null, null);
			} else if (searchStr != null) {
				liste = computerService.getAll((pageNb - 1) * recordsPerPage,
						recordsPerPage, searchStr, orderBy);
			} else {
				liste = computerService.getAll((pageNb - 1) * recordsPerPage,
						recordsPerPage, "", orderBy);
			}
			for (Computer comp : liste) {
				ComputerDto compdto = new ComputerDto();
				listdto.add(compdto.toDto(comp));
			}
			modelAndView.addObject("listOfComputers", listdto);
		} catch (Exception e) {
			logger.error("Mauvaise récupération des ordinateurs", e);
		}

		int noOfRecords = computerService.getCount();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		modelAndView.addObject("totalNbOfComp", noOfRecords);
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
				CompanyDto compdto = new CompanyDto();
				listdto.add(compdto.toDto(company));
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
			computerdto = computerdto.toDto(computer);
			modelAndView.addObject("computer", computerdto);

			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				CompanyDto compdto = new CompanyDto();
				compdto = compdto.toDto(company);
				listdto.add(compdto);
			}
			m.addAttribute("ComputerDto", computerdto);
			modelAndView.addObject("listOfCompanies", listdto);
		} catch (SQLException e) {
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
			computer = computerdto.fromDto(computerdto);
			try {
				computerService.add(computer);
			} catch (SQLException e) {
				logger.error("Mauvais Update", e);
			}
			modelAndView.addObject("message", "Ajout effectué");
			modelAndView.setViewName("redirect:dashboard?main=accueil");
		} else {
			modelAndView.setViewName("addComputer");
			modelAndView.addObject("ComputerDto", computerdto);
			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				CompanyDto compdto = new CompanyDto();
				compdto = compdto.toDto(company);
				listdto.add(compdto);
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
		if (!result.hasErrors()) {
			modelAndView.setViewName("redirect:dashboard?main=accueil");
			if (("Edit".equals(updateButton))||("Edition".equals(updateButton))) {
				logger.info("Displaying dashboard");
				Computer computer = new Computer();
				computer = computerdto.fromDto(computerdto);
				computer.setId(hiddenid);
				try {
					computerService.update(computer);
				} catch (SQLException e) {
					logger.error("Mauvais Update", e);
				}
				modelAndView.addObject("message", "Modification effectuée");
			} else {
				try {
					computerService.delete(hiddenid);
				} catch (Exception e) {
					logger.error("Delete raté", e);
				}
				modelAndView.addObject("message", "Suppression effectuée");
			}
		} else {
			modelAndView.setViewName("editComputer");
			modelAndView.addObject("computer", computerdto);
			List<Company> liste = companyService.getAll();
			List<CompanyDto> listdto = new ArrayList<CompanyDto>();
			for (Company company : liste) {
				CompanyDto compdto = new CompanyDto();
				compdto = compdto.toDto(company);
				listdto.add(compdto);
			}
			modelAndView.addObject("listOfCompanies", listdto);
		}
		return modelAndView;
	}
}
