package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {

	public UserDAO() { super(); connect(); }
	
	public void finalize() { close(); }
	
	public boolean insert(User user) {
		
		boolean status = false;
		
		try {
			
			String sql = "INSERT INTO \"public\".user (name, login, password, email, contributions) "
						 + "VALUES ('" + user.getName() + "', '" + user.getLogin()
						 + "', MD5('"  + user.getPassword() + "'), '" + user.getEmail()
						 + "', " + user.getContributions() + ");";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			st.executeUpdate();
			st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
	
	public User getAll(String login) {
		
		User user = null;
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM \"public\".user WHERE login = '" + login + "';";
			
			ResultSet rs = st.executeQuery(sql);	
			
	        if(rs.next())
	        	user = new User(rs.getInt("id"),
	        					rs.getString("name"),
	        					rs.getString("login"),
	        					rs.getString("password"), 
	        			 		rs.getString("email"), 
	        			        rs.getInt("contributions"));
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return user;
	}
	
	public int getId(String login) {
		
		int id = 0;
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM \"public\".user WHERE login = '" + login + "';";
			
			ResultSet rs = st.executeQuery(sql);
			
			if (rs.next())
				id = rs.getInt("id");
			
			st.close();
			
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return id;
	}
	
	public User check(String login, String password) {
		
		User user = null;
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM \"public\".user WHERE login = '" + login +
						 "' AND password = MD5('" + password + "');";
			
			ResultSet rs = st.executeQuery(sql);
			
			if (rs.next()) {
				
				user = new User(rs.getString("name"),
								rs.getString("login"),
								rs.getString("password"),
								rs.getString("email"),
								rs.getInt("contributions"));
			}
			
			st.close();
			
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return user;
	}
	
	public boolean updateContributions(User user) {
		
		boolean status = false;
		
		try {
			
			String sql = "UPDATE public.user SET contributions = "
						 + user.getContributions() + " WHERE id = "
						 + user.getId();
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			st.executeUpdate(); st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
	
	public List<String> getData(String login) {
		
		List<String> data = new ArrayList<String>();
		
		int userId = this.getId(login);
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
				String sql = "SELECT name, contributions, id FROM public.user WHERE id = " + userId;
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	data.add(rs.getString("name"));
	        	data.add(String.valueOf(rs.getInt("contributions")));
	        	data.add(String.valueOf(rs.getInt("id")));
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return data;
	}
	
	public boolean delete(int id) {
		
		boolean status = false;
		
		try {
			
			Statement st = connection.createStatement();
			
			st.executeUpdate("DELETE FROM public.user WHERE id = " + id);
			st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
}
