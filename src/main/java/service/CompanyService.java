package service;

import dao.CompanyDAO;
import model.Company;
import spark.Request;
import spark.Response;


public class CompanyService {

	private CompanyDAO companyDao = new CompanyDAO();
	
	public int insert(Request request, Response response) {
		
		String name = request.queryParams("company");
		
		Company company = new Company(name);
		
		companyDao.insert(company);
		
		return companyDao.getId(name);
	}
}
