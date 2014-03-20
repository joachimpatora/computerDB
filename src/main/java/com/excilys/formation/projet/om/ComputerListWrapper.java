package com.excilys.formation.projet.om;

import java.util.List;

public class ComputerListWrapper {
	private List<?> computerList;
	private int count;
	
	public List<?> getComputerList() {
		return computerList;
	}
	public void setComputerList(List<?> computerList) {
		this.computerList = computerList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ComputerListWrapper(List<?> computerList, int count) {
		super();
		this.computerList = computerList;
		this.count = count;
	}
	
	public ComputerListWrapper() {
		super();
	}
}
