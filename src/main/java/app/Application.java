package app;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.*;
import service.*;
import java.util.HashMap;

public class Application {
	
	private static UserService userService = new UserService();
	private static ServiceService serviceService = new ServiceService();
	private static CommentService commentService = new CommentService();
	private static CompanyService companyService = new CompanyService();
	private static OfferService offerService = new OfferService();
	private static ForumService forumService = new ForumService();

	public static void main(String[] args) {
		
		port(6789);
		
		staticFiles.location("/public");
		
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		get("/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/index.vm")));
		
		get("/profile/", (request, response)-> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				response.redirect("/");
				
				return "";
				
			} else
				return engine.render(new ModelAndView(model, "public/templates/profile.vm"));
		});
		
		get("/signup/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				response.redirect("/");
				
				return "";
				
			} else
				return engine.render(new ModelAndView(model, "public/templates/signup.vm"));
		});
		
		get("/services/", (request, response) -> serviceService.makeList(""));
		
		get("/submissions/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null)
				return serviceService.makeListSubmissions((String)request.session().raw().getAttribute("login"));

			response.redirect("/profile/");
				
			return "";
		});
		
		get("/submissions/delete/:serviceId/:companyId", (request, response) -> {
			
			offerService.delete(request, response);
			commentService.deleteCommentService(request, response);
			serviceService.delete(request, response);
			
			response.redirect("/submissions/");
			
			return "";
		});
		
		get("/forum/", (request, response)-> {
			
			if (request.session().raw().getAttribute("login") != null)
				return forumService.makeList(request, "comments");
			
			response.redirect("/profile/");
			
			return "";
			
		});
		
		get("/forum/delete/:id", (request, response) -> {
			
			forumService.delete(request, response);
			commentService.deleteCommentForum(request, response);
			
			response.redirect("/forum/");
			
			return "";
		});
		
		get("/forum/insert/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				forumService.insert(request, response);
				
				return forumService.makeList(request, "comments");
				
			} else {
				
				response.redirect("/profile/");
				
				return "";
			}
		});
		
		get("/forum/:id", (request, response) -> forumService.showComments(request, response));
		
		get("/forum/comment/insert/:id", (request, response) -> {
			
			commentService.insertForum(request, response);
			forumService.updateComments(request, response);
			
			return forumService.showComments(request, response);
		});
		
		get("/test/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/add.vm")));
		
		get("/about/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/about.vm")));
		
		get("/add/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null)
				return engine.render(new ModelAndView(model, "public/templates/add.vm"));
				
			response.redirect("/profile/");
				
			return "";
		});
		
		get("/tables/", (request, response) -> companyService.showRanking());
		
		post("/profile/", (request, response) -> {
			
			if (userService.check(request, response)) {
				
				String user = request.queryParams("login");
				
				request.session().raw().setAttribute("login", user);
				response.redirect("/");
				
			} else
				System.out.println("User not found in the database");
			
			return "";
		});
		
		
		post("/signup/", (request, response) -> {
			
			if (userService.insert(request, response)) {
				
				String user = request.queryParams("login");
				
				request.session().raw().setAttribute("login", user);				
				response.redirect("/");
			}
			
			return "";
		});
		
		post("/services/", (request, response) -> serviceService.makeList(request.queryParams("search")));
		
		post("/add/", (request, response) -> {
			
			userService.updateContributions(request, response);
			
			int companyId = companyService.insert(request, response);
			int serviceId = serviceService.insert(request, response);
			
			commentService.insertService(request, response, companyId, serviceId);
			offerService.insert(companyId, serviceId);
			
			response.redirect("/add/");
			
			return "";
		});
	}
}
