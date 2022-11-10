package service;

import dao.ServiceDAO;
import dao.UserDAO;
import model.Service;
import spark.Request;
import spark.Response;

import java.util.Scanner;
import java.util.List;
import java.io.File;

public class ServiceService {
	
	private ServiceDAO serviceDao = new ServiceDAO();
	private UserDAO userDao = new UserDAO();
	private String list;
	
	public ServiceService() {}
	
	public int insert(Request request, Response response) {
		
		String description = request.queryParams("description");
		float price = Float.parseFloat(request.queryParams("price"));
		
		Service service = new Service(description, price);
		
		serviceDao.insert(service);
		
		return serviceDao.getId(description);
	}
	
	public String makeList(String searchTerm) {
		
		list = "";
		
		File filename = new File("services.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		List<List<String>> data = serviceDao.get(searchTerm);
		
		String services = "";
		
		for (List<String> l : data) {
			
			services += "<details open=\"\">\n"
						+ "<summary style=\"width: 100%\">\n"
						+ l.get(0) + " - " + l.get(1) + " - R$" + l.get(2).replace('.', ',') + "\n<br><br>"
						+ "</summary>\n"
						+ l.get(3) + "\n<br><br>"
						+ "<p class=\"userName\">@" + l.get(4) + "</p>\n<br><br>"
						+ "</details>";
		}
		
		list = list.replace("SERVICES", services);
		
		return list;
	}
	
	public String makeListSubmissions(String login) {
		
		list = "";
		
		File filename = new File("submissions.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		int userId = userDao.getId(login);
		
		List<List<String>> data = serviceDao.getByUserId(userId);
		
		String services = "";
		
		for (List<String> l : data) {
			
			services += "<details open=\"\">\n"
						+ "<summary style=\"width: 100%\">\n"
						+ l.get(0) + " - " + l.get(1) + " - R$" + l.get(2).replace('.', ',') + "\n<br><br>"
						+ "</summary>\n"
						+ l.get(3) + "\n<br><br>"
						+ "<p class=\"userName\">" + l.get(4) + "</p><br>"
						+ "<form action=\"/submissions/delete/" + l.get(5) + "/" + l.get(6) + "\" method=\"get\">"
						+ "<input type=\"submit\" class=\"btn botao-padrao\" value=\"Remover\">"
						+ "</form><br><br>"
						+ "</details>";
		}
		
		list = list.replace("SUBMISSIONS", services);
		
		return list;
	}
	
	public boolean delete(Request request, Response response) {
		
		int serviceId = Integer.parseInt(request.params(":serviceId"));
		
		return serviceDao.delete(serviceId);
	}
}
