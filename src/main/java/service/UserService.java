package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import dao.UserDAO;
import model.User;

import spark.Request;
import spark.Response;

public class UserService {
	
	private UserDAO userDao = new UserDAO();
	private String list;
	
	public UserService() {}
	
	public boolean insert(Request request, Response response) {
		
		String name = request.queryParams("name");
		String login = request.queryParams("login");
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		User user = new User(name, login, password, email, 0);
		
		return userDao.insert(user);
	}
	
	public boolean check(Request request, Response response) {
		
		String login = request.queryParams("login");
		String password = request.queryParams("password");
		
		User user = userDao.check(login, password);
		
		if (user != null) return true;
		
		return false;
	}
	
	public boolean updateContributions(Request request, Response response) {
		
		String login = (String)request.session().raw().getAttribute("login");
		
		User user = userDao.getAll(login);
		
		user.setContributions(user.getContributions() + 1);
		
		return userDao.updateContributions(user);
	}
	
	public String showData(Request request, Response response) {
		
		list = "";
		
		File filename = new File("profile.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		List<String> data = userDao.getData((String)request.session().raw().getAttribute("login"));
		
		String user = "";
		
		user += "    <div  class=\"container profile\">\n"
				+ "      <div class=\"header\">\n"
				+ "      <h1>Perfil de " + data.get(0) + "</span></h1>\n"
				+ "    </div>\n"
				+ "    <div class=\"detalhes\">\n"
				+ "      <span class=\"negrito\">Quantidade de serviços: " + data.get(1) + "</span>\n"
				+ "    </div>\n"
				+ "    <div class=\"button\">\n"
				+ "      <h3></h3>\n"
				+ "		 <a href=\"/logout/\" class=\"btn botao-padrao\">Fazer logout</a>\n"
				+ "		 <a href=\"/profile/delete/user/" + data.get(2) + "\" class=\"btn botao-padrao\">Remover conta</a>\n"
				+ "    </div>\n"
				+ "    </div>\n";
		
		list = list.replace("USER", user);
		
		return list;
	}
	
	public boolean delete(Request request, Response response) {
		
		int userId = Integer.parseInt(request.params(":id"));
		
		return userDao.delete(userId);
	}
}
