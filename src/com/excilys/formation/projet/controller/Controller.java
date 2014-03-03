package com.excilys.formation.projet.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.Service;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Service serv = new Service();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{	
		Computer computer = new Computer();
		
		computer.setName(request.getParameter("name"));
		computer.setIntroducedDate(request.getParameter("introducedDate"));
		computer.setDiscontinuedDate(request.getParameter("discontinuedDate"));
		computer.setCompany(request.getParameter("company"));
		
		try {
			serv.getDaoFactory().addComputer(computer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("Complist");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getParameter("id").equals("2"))
		{
			ArrayList<Company> liste = serv.getDaoFactory().getCompanyList();
	        request.setAttribute("listComp", liste);
	        this.getServletContext().getRequestDispatcher( "/addComputer.jsp" ).forward( request, response );
		}
		else if(request.getParameter("id").equals("1"))
		{
			String compid = request.getParameter("compId");
			Computer computer = serv.getDaoFactory().getComputer(new Long(compid));
			request.setAttribute("computer", computer);
			ArrayList<Company> liste = serv.getDaoFactory().getCompanyList();
	        request.setAttribute("listComp", liste);
			this.getServletContext().getRequestDispatcher("/editComputer.jsp").forward(request, response);		
		}
	}
	
}
