package com.excilys.formation.projet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.CompanyService;
import com.excilys.formation.projet.service.ComputerService;

@Controller
@WebServlet("/PrintScrController")
public class PrintScrController extends OverHttpRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(Complist.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
	public PrintScrController() {
		super();
	}

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
		try {
			if (request.getParameter("id").equals("2")) {
				List<Company> liste = companyService.getAll();
				request.setAttribute("listComp", liste);
				this.getServletContext()
						.getRequestDispatcher("/addComputer.jsp")
						.forward(request, response);
			} else if (request.getParameter("id").equals("1")) {
				String compid = request.getParameter("compId");
				Computer computer = computerService.get(new Long(compid));
				request.setAttribute("computer", computer);
				List<Company> liste = companyService.getAll();

				request.setAttribute("listComp", liste);
				this.getServletContext()
						.getRequestDispatcher("/editComputer.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			logger.error("Mauvaise édition", e);
		}
	}

}
