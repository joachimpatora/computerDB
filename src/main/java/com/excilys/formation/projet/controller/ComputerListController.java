package com.excilys.formation.projet.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.projet.om.Computer;

import org.springframework.web.servlet.ModelAndView;

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
	
	
	@RequestMapping(value="/dashboard",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView listOfComputers(@RequestParam(value="orderBy", required=false, defaultValue = "") String orderBy, 
			@RequestParam(value="page", required=false, defaultValue = "1") Long pageNb, 
			@RequestParam(value="search", required=false) String searchStr,
			@RequestParam(value="main", required=false) String main,
			@RequestParam(value="message", required=false) String message) {
		
		Long recordsPerPage = RECORDS_PER_PAGE;
		List<Computer> liste;
		
		
		logger.info("Displaying dashboard");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("dashboard");
		logger.error("Ordinateur recherché", searchStr);
		
		try {
			if ((main != null)&&(main.equals("accueil")))
			{
				liste = computerService.getAll((pageNb - 1)* recordsPerPage, recordsPerPage, null, null);
			}
			else if(searchStr != null)
			{
				liste = computerService.getAll((pageNb - 1)* recordsPerPage, recordsPerPage, searchStr, orderBy);
			}
			else
			{
				liste = computerService.getAll((pageNb - 1)* recordsPerPage, recordsPerPage, "", orderBy);
			}
			modelAndView.addObject("listOfComputers",liste);
		} catch (Exception e) {
			logger.error("Mauvaise récupération des ordinateurs", e);
		}
		int noOfRecords = computerService.getCount();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		modelAndView.addObject("totalNbOfComp", noOfRecords);
		modelAndView.addObject("noOfPages", noOfPages);
		modelAndView.addObject("currentPage", pageNb);
		modelAndView.addObject("message", message);
		modelAndView.addObject("search",searchStr);
		return modelAndView;
	}
	
	@RequestMapping(value="/addComputer",method=RequestMethod.GET)
	public ModelAndView addComputer() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("addComputer");
		try{
			modelAndView.addObject("listComp", companyService.getAll());
		} catch(Exception e) {}
		return modelAndView;
	}
	
	@RequestMapping(value="/editComputer",method=RequestMethod.GET)
	public ModelAndView editComputer(@RequestParam(value="compId", required=true) Long compId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("editComputer");
		try {
			modelAndView.addObject("computer", computerService.get(compId));
			modelAndView.addObject("listComp", companyService.getAll());
		} catch (SQLException e) {
			logger.error("Mauvaise récupération de l'ordinateur à éditer", e);
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/addComputerResult", method=RequestMethod.POST)
	public ModelAndView addComputer(@RequestParam(value="name", required=true) String name,
			@RequestParam(value="introducedDate", required=false) String introducedDate,
			@RequestParam(value="discontinuedDate", required=false) String discontinuedDate,
			@RequestParam(value="company", required=false) String company)
	{
		ModelAndView modelAndView = new ModelAndView("redirect:dashboard?main=accueil");
		logger.info("Displaying dashboard");
		Computer computer = new Computer();
		computer.setName(name);
		computer.setIntroducedDate(introducedDate);
		computer.setDiscontinuedDate(discontinuedDate);
		computer.setCompany(company);
		try {
			computerService.add(computer);
		} catch (SQLException e) {
			logger.error("Mauvais Update", e);
		}
		modelAndView.addObject("message", "Ajout effectué");
		return modelAndView;
	}
	
	@RequestMapping(value="/updateComputer", method=RequestMethod.POST)
	public ModelAndView updateComputer(@RequestParam(value="hiddenid", required=true) Long hiddenid,
			@RequestParam(value="updateButton", required=true) String updateButton,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="introducedDate", required=false) String introducedDate,
			@RequestParam(value="discontinuedDate", required=false) String discontinuedDate,
			@RequestParam(value="company", required=false) String company) {

		ModelAndView modelAndView = new ModelAndView("redirect:dashboard?main=accueil");
		if ("Edit".equals(updateButton)) 
		{
			logger.info("Displaying dashboard");
			Computer computer = new Computer();
			computer.setId(hiddenid);
			computer.setName(name);
			computer.setIntroducedDate(introducedDate);
			computer.setDiscontinuedDate(discontinuedDate);
			computer.setCompany(company);
			try {
				computerService.update(computer);
			} catch (SQLException e) {
				logger.error("Mauvais Update", e);
			}
			modelAndView.addObject("message", "Modification effectuée");
		}
		else
		{
			try {
				computerService.delete(hiddenid);
			}catch(Exception e){
				logger.error("Delete raté", e);
			}
			modelAndView.addObject("message", "Suppression effectuée");
		}
		return modelAndView;
	}
}
