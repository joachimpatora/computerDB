package com.excilys.formation.projet.webservices;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.projet.om.Computer;
import com.excilys.formation.projet.service.ComputerService;

@WebService
@Service("computerWS")
public class ComputerListDisplay {
	@Autowired
	ComputerService computerService;

	@WebMethod
	public List<Computer> findAll() {
		return computerService.findAll();
	}
}
