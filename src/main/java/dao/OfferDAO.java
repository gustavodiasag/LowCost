package dao;

import model.Offer;

import java.sql.*;

public class OfferDAO extends DAO {

	public OfferDAO() { super(); connect(); }
	
	public void finalize() { close(); }
	
	public boolean insert(Offer offer) {
		
		boolean status = false;
		
		try {
			
			String sql = "INSERT INTO \"public\".offer (service_id_fk, company_id_fk) VALUES ("
						 + offer.getServiceId() + ", "
						 + offer.getCompanyId() + ");";
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			st.executeUpdate();
			st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
}
