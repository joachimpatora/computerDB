package com.excilys.formation.projet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.Service;

@WebServlet("/EditControl")
public class EditControl extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Service serv = new Service();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		Computer computer = new Computer();
		
		computer.setId(new Long(request.getParameter("hiddenid")));
		computer.setName(request.getParameter("name"));
		computer.setIntroducedDate(request.getParameter("introducedDate"));
		computer.setDiscontinuedDate(request.getParameter("discontinuedDate"));
		computer.setCompany(request.getParameter("company"));
		
		try {
			serv.getDaoFactory().editComputer(computer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("Complist");
	}
}
