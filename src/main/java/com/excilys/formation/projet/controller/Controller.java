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

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.CompanyService;
import com.excilys.formation.projet.service.ComputerService;
import com.excilys.formation.projet.service.ServiceFactory;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(Complist.class);
	
	ComputerService computerService = ServiceFactory.getInstance().getComputerService();
	CompanyService companyService = ServiceFactory.getInstance().getCompanyService();
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{	
		Computer computer = new Computer();
		
		computer.setName(request.getParameter("name"));
		computer.setIntroducedDate(request.getParameter("introducedDate"));
		computer.setDiscontinuedDate(request.getParameter("discontinuedDate"));
		computer.setCompany(request.getParameter("company"));
		
		try {
			computerService.add(computer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        request.setAttribute("message", "Ajout effectué");
		this.getServletContext().getRequestDispatcher("/Complist").forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			if(request.getParameter("id").equals("2"))
			{
				List<Company> liste = companyService.getAll();
		        request.setAttribute("listComp", liste);
		        this.getServletContext().getRequestDispatcher( "/addComputer.jsp" ).forward( request, response );
			}
			else if(request.getParameter("id").equals("1"))
			{
				String compid = request.getParameter("compId");
				Computer computer = computerService.get(new Long(compid));
				request.setAttribute("computer", computer);
				List<Company> liste = companyService.getAll();
				
		        request.setAttribute("listComp", liste);
				this.getServletContext().getRequestDispatcher("/editComputer.jsp").forward(request, response);		
			}
		} catch(Exception e) {
			logger.error("Mauvaise édition", e);
		}
	}
	
}
