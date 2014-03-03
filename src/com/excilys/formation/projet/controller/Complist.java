package com.excilys.formation.projet.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.Service;

@WebServlet("/Complist")
public class Complist extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Service srvce = new Service();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{	
		if(request.getParameter("search") != null)
		{
			ArrayList<Computer> list = srvce.getDaoFactory().getComputer(request.getParameter("search"));
			request.setAttribute("listFound", list);
			this.getServletContext().getRequestDispatcher( "/dashboard.jsp" ).forward( request, response );
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long page = new Long(1);
        Long recordsPerPage = new Long(10);
        if(request.getParameter("page") != null)
        {
        	page = Long.parseLong(request.getParameter("page"));
        }
            
		ArrayList<Computer> liste = srvce.getDaoFactory().getComputerList((page-1)*recordsPerPage, recordsPerPage);
		int noOfRecords = srvce.getDaoFactory().getNbOfComputers();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		request.setAttribute("totalNbOfComp", noOfRecords);
        request.setAttribute("listOfComputers", liste);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        this.getServletContext().getRequestDispatcher( "/dashboard.jsp" ).forward( request, response );
	}
}
