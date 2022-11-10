package service;

import dao.ServiceDAO;
import model.Service;
import spark.Request;
import spark.Response;

import java.util.Scanner;
import java.util.List;
import java.io.File;

public class ServiceService {
	
	private ServiceDAO serviceDao = new ServiceDAO();
	private String list;
	
	public ServiceService() {}
	
	public boolean insert(Request request, Response response) {
		
		String description = request.queryParams("description");
		float price = Float.parseFloat(request.queryParams("price"));
		
		Service service = new Service(description, price);
		
		if (serviceDao.insert(service)) return true;
		
		return false;
	}
	
	public String makeList() {
		
		list = "";
		
		File filename = new File("services.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		List<List<String>> data = serviceDao.getAll();
		
		String services = "";
		
		for (List<String> l : data) {
			
			services += "<details open=\"\">\n"
						+ "<summary style=\"width: 100%\">\n"
						+ l.get(0) + " - " + l.get(1) + " - R$" + l.get(2).replace('.', ',') + "\n"
						+ "</summary>\n"
						+ l.get(3) + "\n"
						+ "<p class=\"userName\">@" + l.get(4) + "</p>\n"
						+ "</details>";
		}
		
		list = list.replaceFirst("SERVICES", services);
		
		return list;
	}
}
