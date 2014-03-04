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

	ComputerService computerService = ServiceFactory.getInstance().getComputerService();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*if (request.getParameter("search") != null) {*/
			Long page = 1L;
			Long recordsPerPage = 10L;
			
			if (request.getParameter("page") != null) {
				page = Long.parseLong(request.getParameter("page"));
			}

			List<Computer> liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, request.getParameter("search"));
			int noOfRecords = computerService.getCount();
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			request.setAttribute("totalNbOfComp", noOfRecords);
			request.setAttribute("listOfComputers", liste);
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("search",request.getParameter("search"));
			this.getServletContext().getRequestDispatcher("/dashboard.jsp")
					.forward(request, response);
			/*List<Computer> list = computerService.getByName(request.getParameter("search"));

			request.setAttribute("listFound", list);

			this.getServletContext().getRequestDispatcher("/dashboard.jsp")
					.forward(request, response);*/
		//}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long page = 1L;
		Long recordsPerPage = 10L;
		
		if (request.getParameter("page") != null) {
			page = Long.parseLong(request.getParameter("page"));
		}

		List<Computer> liste = computerService.getAll((page - 1)* recordsPerPage, recordsPerPage, "");
		int noOfRecords = computerService.getCount();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		request.setAttribute("totalNbOfComp", noOfRecords);
		request.setAttribute("listOfComputers", liste);
		request.setAttribute("noOfPages", noOfPages);
		request.setAttribute("currentPage", page);
		this.getServletContext().getRequestDispatcher("/dashboard.jsp")
				.forward(request, response);
	}
}
