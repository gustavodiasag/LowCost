package dao;

import model.Company;

import java.sql.*;

public class CompanyDAO extends DAO {
	
	public CompanyDAO() { super(); connect(); }

	public void finalize() { close(); }

	public boolean insert(Company company) {

		boolean status = false;

		try {

			String sql = "INSERT INTO company (name) VALUES ('" + company.getName() + "');";

			PreparedStatement st = connection.prepareStatement(sql);

			st.executeUpdate();
			st.close();;

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}

	public Company get(int id) {

		Company company = null;

		try {

			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM company WHERE id = " + id;

			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) company = new Company(rs.getString("name"));

			st.close();

		} catch (Exception e) { System.err.println(e.getMessage()); }

		return company;
	}

	public boolean update(Company company) {

		boolean status = false;

		try {

			String sql = "UPDATE company SET name = '" + company.getName() + "' WHERE id = " + company.getId();

			PreparedStatement st = connection.prepareStatement(sql);

			st.executeUpdate();
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}
	
	public boolean delete(int id) {

		boolean status = false;

		try {

			Statement st = connection.createStatement();

			st.executeUpdate("DELETE FROM company WHERE id = " + id);
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	} 
} 
