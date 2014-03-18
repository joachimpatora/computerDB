package com.excilys.formation.projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.projet.om.Company;
import com.excilys.formation.projet.om.Computer;

@Repository
public class ComputerDao {

	final private Long ASC = 0L;
	final private Long DESC = 1L;

	@Autowired
	private MonitorDbDao monitor;

	Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	private int nbofcomputers = 0;
	private String searchCache = "";
	Long old_offset = 0L;
	Long orderByDir = ASC;
	String old_orderBy = "";
	String OrderByDirection = "ASC";

	public ComputerDao() {
		super();
	}

	public ArrayList<Computer> getAll() {
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection conn;
		try {
			conn = ConnectionDB.getConnection();
			stmt = conn
					.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id;");
			monitor.addLog(conn, 0L, "List of computers transmitted.");
			rs = stmt.executeQuery();

			while (rs.next()) {
				Company company = new Company(rs.getString(5), new Long(
						rs.getString(6)));
				Computer computers = new Computer();
				computers.setId(new Long(rs.getString(1)));
				computers.setName(rs.getString(2));
				computers.setIntroducedDate(new LocalDate(rs.getDate(3)));
				computers.setDiscontinuedDate(new LocalDate(rs.getDate(4)));

				computers.setCompany(company);

				list.add(computers);
			}
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<Computer> getAll(Long offset, Long noOfRecords,
			String searchStr, String orderBy) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
		ArrayList<Computer> list = new ArrayList<Computer>();
		ResultSet rs = null;
		PreparedStatement stmt = null;

		if (searchStr == null) {
			searchStr = "";
			searchCache = "";
		} else if ((searchStr != null) && (!searchStr.equals(""))) {
			if (offset != 0) {
				old_offset = offset;
				if (!searchCache.equals("")) {
					searchStr = searchCache;
				}
			} else if ((old_offset != 0) && (offset == 0)) {
				if (!searchCache.equals("")) {
					searchStr = searchCache;
				}
			}
		} else if (!searchCache.equals("")) {
			searchStr = searchCache;
		} else {
			searchCache = "";
			old_offset = 0L;
		}
		try {
			String sqlFormat;
			final String SQL = "SELECT SQL_CALC_FOUND_ROWS computer.id, computer.name, introduced, discontinued, company.name, computer.company_id FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.name LIKE ? ORDER BY %s %s LIMIT ?, ? ; ";

			if (orderBy != null) {
				if (orderBy.equals("")) {
					orderBy = old_orderBy;
					orderByDir = Math.abs(orderByDir - 1L);
				} else {
					if (orderByDir == ASC) {
						orderByDir = DESC;
						OrderByDirection = "ASC";
					} else {
						orderByDir = ASC;
						OrderByDirection = "DESC";
					}
				}
				if (orderBy.equals("Name")) {
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.name",
							OrderByDirection);
				} else if (orderBy.equals("IntroDate")) {
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.introduced",
							OrderByDirection);
				} else if (orderBy.equals("OutroDate")) {
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.discontinued",
							OrderByDirection);
				} else if (orderBy.equals("Company")) {
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.company_id",
							OrderByDirection);
				} else {
					old_orderBy = orderBy;
					sqlFormat = String.format(SQL, "computer.id",
							OrderByDirection);
				}
			} else {
				old_orderBy = "";
				sqlFormat = String.format(SQL, "computer.id", OrderByDirection);
			}
			stmt = conn.prepareStatement(sqlFormat);
			monitor.addLog(conn, 0L, "List of computers transmitted.");
			if (searchStr != null) {

				searchStr = "%" + searchStr + "%";
				searchCache = searchStr;
				stmt.setString(1, searchStr);
			} else {
				stmt.setString(1, "%" + "%");
			}

			stmt.setLong(2, offset);
			stmt.setLong(3, noOfRecords);
			rs = stmt.executeQuery();
			Long templong;
			while (rs.next()) {
				logger.debug(rs.getString(6));
				Computer computers = new Computer();
				computers.setId(new Long(rs.getString(1)));
				computers.setName(rs.getString(2));
				computers.setIntroducedDate(new LocalDate(rs.getDate(3)));
				computers.setDiscontinuedDate(new LocalDate(rs.getDate(4)));
				if (rs.getString(6) != null) {
					templong = new Long(rs.getString(6));
				} else {
					templong = 0L;
				}
				Company company = new Company(rs.getString(5), templong);
				computers.setCompany(company);

				list.add(computers);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
			}
			rs = stmt.executeQuery("SELECT FOUND_ROWS()");
			if (rs.next()) {
				this.nbofcomputers = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	public int getNbOfComputers() {
		return this.nbofcomputers;
	}

	public Computer get(Long id) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
		Computer computer = new Computer();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if (id != null) {
			try {
				stmt = conn
						.prepareStatement("SELECT computer.id, computer.name, introduced, discontinued, company.name, computer.company_id FROM computer LEFT OUTER JOIN company ON company.id = computer.company_id WHERE computer.id=?; ");
				stmt.setLong(1, id);
				rs = stmt.executeQuery();
				Long templong;
				while (rs.next()) {
					computer.setId(new Long(rs.getString(1)));
					computer.setName(rs.getString(2));
					computer.setIntroducedDate(new LocalDate(rs.getDate(3)));
					computer.setDiscontinuedDate(new LocalDate(rs.getDate(4)));
					if (rs.getString(6) != null) {
						templong = new Long(rs.getString(6));
					} else {
						templong = 0L;
					}
					Company company = new Company(rs.getString(5), templong);
					computer.setCompany(company);
				}
				monitor.addLog(conn, 0L, "Computer transmitted.");
			} catch (Exception e) {
				logger.error("Erreur lors du traitement SQL.", e);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return computer;
	}

	public void delete(Long id) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if (id == null)
			return;

		try {
			stmt = conn
					.prepareStatement("DELETE FROM computer WHERE computer.id = ?");
			stmt.setLong(1, id);

			stmt.executeUpdate();
			monitor.addLog(conn, 0L, "Computer deleted.");

		} catch (Exception e) {
			logger.error("Erreur lors du traitement SQL de suppression.", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public void update(Computer computer) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Long companyid = null;
		java.sql.Date introduced;
		java.sql.Date discontinued;
		if (computer.getIntroducedDate()!=null) {
			introduced = new java.sql.Date(computer
				.getIntroducedDate().toDate().getTime());
		}
		else {
			introduced = null;
		}
		if(computer.getDiscontinuedDate() != null) {
			discontinued = new java.sql.Date(computer
				.getDiscontinuedDate().toDate().getTime());
		}
		else {
			discontinued = null;
		}
		if ((computer.getCompany() != null)
				&& (computer.getCompany().getId() != null)
				&& ((new Long(computer.getCompany().getId())) != 0)) {
			companyid = new Long(computer.getCompany().getId());
		}

		try {
			stmt = conn
					.prepareStatement("UPDATE computer SET name = ?, introduced = ? ,discontinued = ?, company_id = ? WHERE computer.id = ?");
			stmt.setString(1, computer.getName());

			if ((computer.getIntroducedDate() == null)
					|| (computer.getIntroducedDate().equals(""))) {
				stmt.setNull(2, java.sql.Types.DATE);
			} else {
				stmt.setDate(2, introduced);
			}
			if ((computer.getDiscontinuedDate() == null)
					|| (computer.getDiscontinuedDate().equals(""))) {
				stmt.setNull(3, java.sql.Types.DATE);
			} else {
				stmt.setDate(3, discontinued);
			}
			if (companyid != null) {
				stmt.setString(4, companyid.toString());
			} else {
				stmt.setNull(4, java.sql.Types.NULL);
			}
			stmt.setLong(5, computer.getId());

			stmt.executeUpdate();
			monitor.addLog(conn, 0L, "Computer updated.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Autowired
	public void add(Computer computer) throws SQLException {
		Connection conn = ConnectionDB.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Long companyid = null;
		java.sql.Date introduced;
		java.sql.Date discontinued;
		if (computer.getIntroducedDate() != null) {
			introduced = new java.sql.Date(computer.getIntroducedDate()
					.toDate().getTime());
		} else {
			introduced = new java.sql.Date(0L);
		}
		if (computer.getDiscontinuedDate() != null) {
			discontinued = new java.sql.Date(computer.getDiscontinuedDate()
					.toDate().getTime());
		} else {
			discontinued = new java.sql.Date(0L);
		}
		if ((computer.getCompany() != null)
				&& (computer.getCompany().getId() != null)
				&& ((new Long(computer.getCompany().getId())) != 0)) {
			companyid = new Long(computer.getCompany().getId());
		}

		try {
			stmt = conn
					.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);");

			stmt.setString(1, computer.getName());
			if ((computer.getIntroducedDate() == null)
					|| (computer.getIntroducedDate().equals(""))) {
				stmt.setNull(2, java.sql.Types.DATE);
			} else {
				stmt.setDate(2, introduced);
			}
			if ((computer.getDiscontinuedDate() == null)
					|| (computer.getDiscontinuedDate().equals(""))) {
				stmt.setNull(3, java.sql.Types.DATE);
			} else {
				stmt.setDate(3, discontinued);
			}
			if (companyid != null) {
				stmt.setString(4, companyid.toString());
			} else {
				stmt.setNull(4, java.sql.Types.NULL);
			}

			stmt.executeUpdate();
			monitor.addLog(conn, 0L, "Computer added.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				conn.close();
			} catch (SQLException e) {
			}
		}

	}
}
