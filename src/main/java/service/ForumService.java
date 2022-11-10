package service;

import dao.ForumDAO;
import dao.UserDAO;
import model.Forum;
import spark.Request;
import spark.Response;

import java.util.Scanner;
import java.util.List;
import java.io.File;

public class ForumService {
	
	private ForumDAO forumDao = new ForumDAO();
	private UserDAO userDao = new UserDAO();
	private String list;
	
	public ForumService() {}
	
	public boolean insert(Request request, Response response) {
		
		String name = request.queryParams("name");
		int userId = userDao.getId((String)request.session().raw().getAttribute("login"));
		
		Forum forum = new Forum(name, 0, userId);
		
		return forumDao.insert(forum);
	}
	
	public String makeList(String orderBy) {
		
		list = "";
		
		File filename = new File("forum.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch(Exception e) { System.err.println(e.getMessage()); }
		
		List<List<String>> data = forumDao.getAll(orderBy);
		
		String forums = "";
		
		for (List<String> l : data) {
			
			forums += "<div class=\"card text-center\">\n"
					  + "  <div class=\"card-body\">\n"
					  + "    <h5 class=\"card-title\">" + l.get(0) + "</h5>\n"
					  + "    <p class=\"card-text\">" + l.get(1) +"</p>\n"
					  + "    <a href=\"thread.html?1\" class=\"btn botao-padrao\">Saber Mais</a>\n"
					  + "  </div>";
		}
		
		list = list.replace("FORUMS", forums);
		
		return list;
	}
}
