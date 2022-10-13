package service;

import dao.UserDAO;
import model.User;

import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.template.velocity.*;

import java.util.HashMap;

public class UserService {
	
	HashMap<String, Object> model = new HashMap<String, Object>();
	
	private UserDAO userDAO = new UserDAO();
	
	public UserService() {}
	
	public int insert(Request request, Response response) {
		
		String name = request.queryParams("name");
		String login = request.queryParams("login");
		String email = request.queryParams("email");
		String password = request.queryParams("password");
		
		User user = new User(name, login, password, email, 0);
		
		if (userDAO.insert(user)) return user.getId();
		
		return -1;
	}
	
	public boolean getToAuth(Request request, Response response) {
		
		String login = request.queryParams("login");
		String password = request.queryParams("password");
		
		return userDAO.getToAuth(login, password);
	}
}
