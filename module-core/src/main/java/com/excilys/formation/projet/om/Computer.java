package com.excilys.formation.projet.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "introduced")
	@Temporal(TemporalType.DATE)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate introducedDate;
	@Column(name = "discontinued")
	@Temporal(TemporalType.DATE)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate discontinuedDate;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Company company;

	public Computer() {
		super();
	}

	public Computer(Long id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public Computer(Long id, String name, LocalDate introducedDate,
			LocalDate discontinuedDate, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LocalDate getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(LocalDate discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

}
