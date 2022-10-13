package dao;

import model.Service;

import java.sql.*;

public class ServiceDAO extends DAO {

	public ServiceDAO() { super(); connect(); }

	public void finalize() { close(); }

	public boolean insert(Service service) {

		boolean status = false;

		try {

			String sql = "INSERT INTO service (description, price) "
						 + "VALUES ('" + service.getDescription() + "', "
						 + service.getPrice() + ");";
			
			PreparedStatement st = connection.prepareStatement(sql);

			st.executeUpdate();
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}

	public Service get(int id) {
		
		Service service = null;

		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM service WHERE id = " + id;

			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) service = new Service(rs.getString("description"),
												(float)rs.getDouble("price"));

			st.close();

		} catch (Exception e) { System.err.println(e.getMessage()); }

		return service;
	}

	public boolean update(Service service) {

		boolean status = false;

		try {
			
			String sql = "UPDATE service SET description = '"
						 + service.getDescription() + "', price = "
						 + service.getPrice() + " WHERE id = "
						 + service.getId();

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

			st.executeUpdate("DELETE FROM service WHERE id = " + id);
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}
}

