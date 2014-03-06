package com.excilys.formation.projet.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.ComputerService;
import com.excilys.formation.projet.service.ServiceFactory;

@WebServlet("/Complist")
public class Complist extends HttpServlet {
	
	Logger logger = LoggerFactory.getLogger(Complist.class);

	ComputerService computerService = ServiceFactory.getInstance().getComputerService();

	/**
	 * 
	 */
	private final Long RECORDS_PER_PAGE = 15L;
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Long page = 1L;
		Long recordsPerPage = RECORDS_PER_PAGE;
		
		if (request.getParameter("page") != null) {
			page = Long.parseLong(request.getParameter("page"));
		}
		try {
			List<Computer> liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, request.getParameter("search"), request.getParameter("orderBy"));
			int noOfRecords = computerService.getCount();
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			request.setAttribute("totalNbOfComp", noOfRecords);
			request.setAttribute("listOfComputers", liste);
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("search",request.getParameter("search"));
			this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Mauvaise récupération des ordinateurs", e);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Long page = 1L;
		Long recordsPerPage = RECORDS_PER_PAGE;
		List<Computer> liste;
		String orderBy = "";
		
		if((request.getParameter("orderBy")) != null)
		{
			orderBy = request.getParameter("orderBy");
		}
		try {
			
			if (request.getParameter("page") != null) {
				page = Long.parseLong(request.getParameter("page"));
			}
			if ((request.getParameter("main") != null)&&(request.getParameter("main").equals("accueil")))
			{
				liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, null, null);
			}
			else if(request.getParameter("search") != null)
			{
				liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, request.getParameter("search"), orderBy);
			}
			else
			{
				liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, "", orderBy);
			}
			int noOfRecords = computerService.getCount();
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			request.setAttribute("totalNbOfComp", noOfRecords);
			request.setAttribute("listOfComputers", liste);
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("search",request.getParameter("search"));
			request.setAttribute("message", request.getParameter("message"));
			this.getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error("Mauvaise récupération des ordinateurs", e);
		}
	}
}
