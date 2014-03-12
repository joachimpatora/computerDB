package com.excilys.formation.projet.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.ComputerService;

@Controller
@WebServlet("/EditControl")
public class EditControl extends OverHttpRequest {
	
	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(Complist.class);
	
	@Autowired
	private ComputerService computerService;
	
	public EditControl(){
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("EDIT".equals(request.getParameter("action"))) {
			Computer computer = new Computer();

			computer.setId(new Long(request.getParameter("hiddenid")));
			computer.setName(request.getParameter("name"));
			computer.setIntroducedDate(request.getParameter("introducedDate"));
			computer.setDiscontinuedDate(request
					.getParameter("discontinuedDate"));
			computer.setCompany(request.getParameter("company"));

			try {
				computerService.update(computer);
			} catch (SQLException e) {
				logger.error("Mauvais Update", e);
			}
			request.setAttribute("message", "Modification effectuée");
			this.getServletContext().getRequestDispatcher("/Complist").forward(request, response);

		} else if ("DELETE".equals(request.getParameter("action"))) {

			try {
				computerService.delete(Long.parseLong(request.getParameter("hiddenid")));
			} catch (NumberFormatException e) {
				logger.error("Delete raté", e);
			} catch (SQLException e) {
				logger.error("Delete raté", e);
			}
			request.setAttribute("message", "Suppression réalisée.");
			this.getServletContext().getRequestDispatcher("/Complist").forward(request, response);
		}

	}
}
