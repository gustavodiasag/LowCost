package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import dao.CompanyDAO;
import model.Company;
import spark.Request;
import spark.Response;


public class CompanyService {

	private CompanyDAO companyDao = new CompanyDAO();
	private String list;
	
	public int insert(Request request, Response response) {
		
		String name = request.queryParams("company");
		
		Company company = new Company(name);
		
		companyDao.insert(company);
		
		return companyDao.getId(name);
	}
	
	public String showRanking() {

		list = "";
		
		File filename = new File("tables.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		List<List<String>> data = companyDao.getRanking();
		
		String companies = "";
		
		int i = 1;
		
		for (List<String> l : data) {
			
			companies += "<tr>\n"
					+ "		<td>" + i++ + "</td>\n"
					+ "		<td>" + l.get(0) + "</td>\n"
					+ "	 </tr>";
		}
		
		list = list.replace("COMPANIES", companies);
		
		return list;
	}
}
