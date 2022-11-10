package dao;

import model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO extends DAO {

	public ServiceDAO() { super(); connect(); }

	public void finalize() { close(); }

	public boolean insert(Service service) {

		boolean status = false;

		try {

			String sql = "INSERT INTO service (description, price) VALUES ('"
						 + service.getDescription() + "', "
						 + service.getPrice() + ");";
			
			PreparedStatement st = connection.prepareStatement(sql);

			st.executeUpdate();
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}
	
	public List<List<String>> get(String searchTerm) {
		
		List<List<String>> services = new ArrayList<List<String>>();
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
				String sql = "SELECT public.company.name, description, price, content, login FROM public.comment "
							 + "JOIN public.user ON public.comment.user_id_fk = public.user.id "
							 + "JOIN service ON public.comment.service_id_fk = public.service.id "
							 + "JOIN company ON public.comment.company_id_fk = public.company.id "
							 + ((searchTerm.isEmpty()) ? "" : "WHERE description = '" + searchTerm + "' ")
							 + "ORDER BY sentiment DESC;";
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	List<String> data = new ArrayList<String>();
	        	
	        	data.add(rs.getString("name"));
	        	data.add(rs.getString("description"));
	        	data.add(String.valueOf(rs.getDouble("price")));
	        	data.add(rs.getString("content"));
	        	data.add(rs.getString("login"));
	        	
	        	services.add(data);
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return services;
	}
	
	public List<List<String>> getByUserId(int userId) {
		
		List<List<String>> services = new ArrayList<List<String>>();
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
				String sql = "SELECT public.company.name, description, price, content, submission," + ""
							 + "public.service.id AS serviceId, public.company.id AS companyId FROM public.comment "
							 + "JOIN public.user ON public.comment.user_id_fk = public.user.id "
							 + "JOIN service ON public.comment.service_id_fk = public.service.id "
							 + "JOIN company ON public.comment.company_id_fk = public.company.id "
							 + "WHERE public.comment.user_id_fk = " + userId + " "
							 + "ORDER BY sentiment DESC;";
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	List<String> data = new ArrayList<String>();
	        	
	        	data.add(rs.getString("name"));
	        	data.add(rs.getString("description"));
	        	data.add(String.valueOf(rs.getDouble("price")));
	        	data.add(rs.getString("content"));
	        	data.add(String.valueOf(rs.getTimestamp("submission").toLocalDateTime()));
	        	data.add(String.valueOf(rs.getInt("serviceId")));
	        	data.add(String.valueOf(rs.getInt("companyId")));
	        	
	        	services.add(data);
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return services;
	}

	public int getId(String description) {
		
		int id = 0;

		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM service WHERE description = '" + description + "';";

			ResultSet rs = st.executeQuery(sql);

			if (rs.next())
				id = rs.getInt("id");

			st.close();

		} catch (Exception e) { System.err.println(e.getMessage()); }

		return id;
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