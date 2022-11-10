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
		
		get("/services/", (request, response) -> {
			
			response.header("Content-Type", "text/html");
		    response.header("Content-Encoding", "UTF-8");
		    
		    return serviceService.makeList(""); 
		});
		
		get("/submissions/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				response.header("Content-Type", "text/html");
				response.header("Content-Encoding", "UTF-8");
				
				return serviceService.makeListSubmissions((String)request.session().raw().getAttribute("login"));

			} else {
				
				response.redirect("/profile/");
				
				return "";
			}
			
		});
		
		get("/submissions/delete/:serviceId/:companyId", (request, response) -> {
			
			offerService.delete(request, response);
			commentService.deleteCommentService(request, response);
			serviceService.delete(request, response);
			
			response.redirect("/submissions/");
			
			return "";
		});
		
		get("/forum/", (request, response)-> {
			
			return forumService.makeList("comments");
		});
		
		get("/forum/insert/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				forumService.insert(request, response);
				
				return forumService.makeList("comments");
				
			} else {
				
				response.redirect("/profile/");
				
				return "";
			}
		});
		
		get("/about/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/about.vm")));
		
		get("/add/", (request, response) -> {
			
			if (request.session().raw().getAttribute("login") != null) {
				
				return engine.render(new ModelAndView(model, "public/templates/add.vm"));
				
			} else {
				
				response.redirect("/profile/");
				
				return "";
				
			}
		});
		
		get("/tables/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/tables.vm")));
		
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
				
			} else
				System.out.println("User could not be inserted to the database");
			
			return "";
		});
		
		post("/services/", (request, response) -> {
			
			response.header("Content-Type", "text/html");
		    response.header("Content-Encoding", "UTF-8");
		    
		    return serviceService.makeList(request.queryParams("search"));
		});
		
		post("/add/", (request, response) -> {
			
			userService.updateContributions(request, response);
			
			int companyId = companyService.insert(request, response);
			int serviceId = serviceService.insert(request, response);
			
			commentService.insert(request, response, companyId, serviceId);
			offerService.insert(companyId, serviceId);
			
			response.redirect("/add/");
			
			return "";
		});
	}
}
