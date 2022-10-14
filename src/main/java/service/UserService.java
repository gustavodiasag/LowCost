package service;

import dao.UserDAO;
import model.User;

import spark.Request;
import spark.Response;

import java.util.HashMap;

public class UserService {
	
	HashMap<String, Object> model = new HashMap<String, Object>();
	
	private UserDAO userDAO = new UserDAO();
	
	public UserService() {}
	
	public String insert(Request request, Response response) {
		
		String name = request.queryParams("name");
		String login = request.queryParams("login");
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		User user = new User(name, login, password, email, 0);
		
		if (userDAO.insert(user)) return user.getLogin();
		
		return null;
	}
	
	public String check(Request request, Response response) {
		
		String login = request.queryParams("login");
		String password = request.queryParams("password");
		
		User user = userDAO.check(login);
		
		if (user.getPassword().compareTo(password) == 0) return user.getLogin();
		
		return null;
	}
}
