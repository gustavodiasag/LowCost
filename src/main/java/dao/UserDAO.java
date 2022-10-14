package dao;

import model.User;

import java.sql.*;

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
	
	public User get(int id) {
		
		User user = null;
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM \"public\".user WHERE id = " + id;
			
			ResultSet rs = st.executeQuery(sql);
			
			if (rs.next()) {
				
				user = new User(rs.getString("name"), rs.getString("login"), rs.getString("password"),
						   		  rs.getString("email"), rs.getInt("contributions"));
			}
			
			st.close();
			
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return user;
	}
	
	public User check(String login) {
		
		User user = null;
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT * FROM \"public\".user WHERE login = '" + login + "';";
			
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
	
	public boolean update(User user) {
		
		boolean status = false;
		
		try {
			
			String sql = "UPDATE user SET name = '" + user.getName()
						  + "', login = '" + user.getLogin()
						  + "', password = MD5('" + user.getPassword()
						  + "'), email = '" + user.getEmail()
						  + "', contributions = " + user.getContributions()
						  + "' WHERE id = " + user.getId();
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			st.executeUpdate(); st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
	
	public boolean delete(int id) {
		
		boolean status = false;
		
		try {
			
			Statement st = connection.createStatement();
			
			st.executeUpdate("DELETE FROM user WHERE id = " + id); st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
}
