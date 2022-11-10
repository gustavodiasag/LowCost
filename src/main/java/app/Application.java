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

	public static void main(String[] args) {
		
		port(6789);
		
		staticFiles.location("/public");
		
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		get("/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/index.vm")));
		
		get("/profile/", (request, response)-> engine.render(new ModelAndView(model, "public/templates/profile.vm")));
		
		post("/profile/", (request, response) -> {
			
			if (userService.check(request, response)) {
				
				String user = request.queryParams("login");
				
				request.session().raw().setAttribute("login", user);
				response.redirect("/");
				
			} else
				System.out.println("User not found in the database");
			
			return "";
		});
		
		get("/signup/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/signup.vm")));
		
		post("/signup/", (request, response) -> {
			
			if (userService.insert(request, response)) {
				
				String user = request.queryParams("login");
				
				request.session().raw().setAttribute("login", user);				
				response.redirect("/");
				
			} else
				System.out.println("User could not be inserted to the database");
			
			return "";
		});
		
		get("/services/", (request, response) -> {
			
			response.header("Content-Type", "text/html");
		    response.header("Content-Encoding", "UTF-8");
		    
		    return serviceService.makeList(); 
		});
		
		get("/forum/", (request, response)-> engine.render(new ModelAndView(model, "public/templates/forum.vm")));
		
		get("/about/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/about.vm")));
		
		get("/add/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/add.vm")));
		
		post("/add/", (request, response) -> {
			
			userService.updateContributions(request, response);
			companyService.insert(request, response);
			serviceService.insert(request, response);
			commentService.insert(request, response);
			offerService.insert(request, response);
			
			response.redirect("/add/");
			
			return "";
		});
		
		get("/tables/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/tables.vm")));
	}
}
