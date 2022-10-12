package dao;

import java.sql.*;

public class DAO {
	
	protected Connection connection;
	
	public DAO() { connection = null; }
	
	public boolean connect( ) {
		
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String myDataBase = "lowcost";
		int port = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + port + "/" + myDataBase;
		String username = "ti2cc";
		String password = "ti@cc";
		
		boolean status = false;
		
		try {
			
			Class.forName(driverName);
			
			connection = DriverManager.getConnection(url, username, password);
			
			status = (connection == null);
			
			System.out.println("Successfully connected to postgres");
			
		} catch (ClassNotFoundException e) { System.err.println("Could not connect: " + e.getMessage());
		
		} catch (SQLException e) { System.err.println("Could not connect: " + e.getMessage()); }
	
		return status;
	}
	
	public boolean close() {
		
		boolean status = false;
		
		try { connection.close(); status = true;
			
		} catch (SQLException e) { System.err.println(e.getMessage()); }
		
		return status;
	}
}
