package service;

import dao.OfferDAO;
import dao.ServiceDAO;
import dao.CompanyDAO;
import model.Offer;
import spark.Request;
import spark.Response;


public class OfferService {
	
	private OfferDAO offerDao = new OfferDAO();
	private ServiceDAO serviceDao = new ServiceDAO();
	private CompanyDAO companyDao = new CompanyDAO();
	
	public boolean insert(Request request, Response response) {
		
		int serviceId = serviceDao.getId((String)request.queryParams("description"));
		int companyId = companyDao.getId((String)request.queryParams("company"));
		
		Offer offer = new Offer(companyId, serviceId);
		
		return offerDao.insert(offer);
	}

}
