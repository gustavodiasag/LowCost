package service;

import dao.UserDAO;
import model.User;

import spark.Request;
import spark.Response;

public class UserService {
	
	private UserDAO userDao = new UserDAO();
	
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
}
