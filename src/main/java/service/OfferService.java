package service;

import dao.OfferDAO;
import model.Offer;

import spark.Request;
import spark.Response;

public class OfferService {
	
	private OfferDAO offerDao = new OfferDAO();

	public boolean insert(int companyId, int serviceId) {
		
		Offer offer = new Offer(companyId, serviceId);
		
		return offerDao.insert(offer);
	}
	
	public boolean delete(Request request, Response response) {
		
		int serviceId = Integer.parseInt(request.params(":serviceId"));
		int companyId = Integer.parseInt(request.params(":companyId"));
		
		return offerDao.delete(serviceId, companyId);
	}

}
