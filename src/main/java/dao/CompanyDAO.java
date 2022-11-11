package dao;

import model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO extends DAO {
	
	public CompanyDAO() { super(); connect(); }

	public void finalize() { close(); }

	public boolean insert(Company company) {

		boolean status = false;
		
		if (this.getId(company.getName()) != 0) return true;
		
		else {
			
			try {
				
				String sql = "INSERT INTO company (name) VALUES ('" + company.getName() + "');";
				
				PreparedStatement st = connection.prepareStatement(sql);
				
				st.executeUpdate();
				st.close();;
				
				status = true;
				
			} catch (SQLException e) { throw new RuntimeException(e); }			
		}
		
		return status;
	}

	public int getId(String name) {

		int id = 0;

		try {

			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM company WHERE name = '" + name + "';";

			ResultSet rs = st.executeQuery(sql);

			if (rs.next())
				id = rs.getInt("id");

			st.close();

		} catch (Exception e) { System.err.println(e.getMessage()); }

		return id;
	}
	
	public List<List<String>> getRanking() {
		
		List<List<String>> companies = new ArrayList<List<String>>();
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
				String sql = "SELECT name, SUM(sentiment) AS sum FROM public.company "
							 + "JOIN comment ON public.company.id = public.comment.company_id_fk "
							 + "GROUP BY name ORDER BY sum DESC;";
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	List<String> data = new ArrayList<String>();
	        	
	        	data.add(rs.getString("name"));
	        	
	        	companies.add(data);
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return companies;
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