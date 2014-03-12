package com.excilys.formation.projet.controller;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Controller
@WebServlet("/OverHttpRequest")
public class OverHttpRequest extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public OverHttpRequest() {
        super();
    }
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
}
