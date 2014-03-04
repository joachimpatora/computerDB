package com.excilys.formation.projet.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.ComputerService;
import com.excilys.formation.projet.service.ServiceFactory;

@WebServlet("/EditControl")
public class EditControl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ComputerService computerService = ServiceFactory.getInstance()
			.getComputerService();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println(request.getParameter("action"));

		if ("EDIT".equals(request.getParameter("action"))) {
			Computer computer = new Computer();

			computer.setId(new Long(request.getParameter("hiddenid")));
			computer.setName(request.getParameter("name"));
			computer.setIntroducedDate(request.getParameter("introducedDate"));
			computer.setDiscontinuedDate(request
					.getParameter("discontinuedDate"));
			computer.setCompany(request.getParameter("company"));

			computerService.update(computer);

			response.sendRedirect("Complist");

		} else if ("DELETE".equals(request.getParameter("action"))) {

			computerService.delete(Long.parseLong(request
					.getParameter("hiddenid")));

			response.sendRedirect("Complist");
		}

	}
}
