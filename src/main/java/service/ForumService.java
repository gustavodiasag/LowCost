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
	
	public String makeList(Request request, String orderBy) {
		
		String user = (String)request.session().raw().getAttribute("login");
		
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
					  + "    <p class=\"card-text\">" + l.get(1) + "</p>\n"
					  + "    <a href=\"/forum/" + l.get(2) + "\" class=\"btn botao-padrao\">Saber Mais</a>\n"
					  + ((user.compareTo(l.get(3)) == 0) ? "<a href=\"/forum/delete/" + l.get(2) + "\" class=\"btn botao-padrao\">Remover</a>\n" : "\n")
					  + "  </div>"
					  + "</div>";
		}
		
		list = list.replace("FORUMS", forums);
		
		return list;
	}
	
	public String showComments(Request request, Response response) {
		
		int forumId = Integer.parseInt(request.params(":id"));
		
		String forumTitle = forumDao.getTitle(forumId);
		
		list = "";
		
		File filename = new File("singleforum.vm");
		
		try {
			
			Scanner in = new Scanner(filename);
			
			while(in.hasNext())
				list += in.nextLine() + "\n";
			
			in.close();
			
		} catch(Exception e) { System.err.println(e.getMessage()); }
		
		List<List<String>> data = forumDao.getComments(forumId);
		
		String comments = "";
		
		for (List<String> l : data) {
			
			comments += "<li class=\"list-group-item\">\n"
					+ "                  <div class=\"top-comment\">\n"
					+ "                      <p class=\"user\">\n"
					+							l.get(1)                         
					+ "                      </p>\n"
					+ "                  </div>\n"
					+ "                  <div class=\"comment-content\">\n"
					+ 							l.get(0)
					+ "                  </div>\n"
					+ "        </li>";
		}
		
		list = list.replace("INSERT_COMMENT", "/forum/comment/insert/" + forumId);
		list = list.replace("TITLE", forumTitle);
		list = list.replace("COMMENTS", comments);
		
		return list;
	}
	
	public boolean updateComments(Request request, Response response) {
		
		int forumId = Integer.parseInt(request.params(":id"));
		
		return forumDao.updateComments(forumId);
	}
	
	public boolean delete(Request request, Response response) {
		
		int forumId = Integer.parseInt(request.params(":id"));
		
		return forumDao.delete(forumId);
	}
	
	public boolean deleteFromUser(Request request, Response response) {
		
		int userId = Integer.parseInt(request.params(":id"));
		
		return forumDao.deleteFromUser(userId);
	}
}
