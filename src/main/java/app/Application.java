package app;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.*;

import service.*;

import java.util.HashMap;

public class Application {
	
	private static UserService userService = new UserService();

	public static void main(String[] args) {
		
		port(6789);
		
		staticFiles.location("/public");
		
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		get("/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/index.vm")));
		
		//get("/:id", (request, response) -> userService.get(request, response));
		
		get("/profile/", (request, response)-> engine.render(new ModelAndView(model, "public/templates/profile.vm")));
		
		get("/services/", (request, response)-> engine.render(new ModelAndView(model, "public/templates/services.vm")));
		
		get("/forum/", (request, response)-> engine.render(new ModelAndView(model, "public/templates/forum.vm")));
		
		get("/about/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/about.vm")));
		
		get("/tables/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/tables.vm")));
		
		get("/signup/", (request, response) -> engine.render(new ModelAndView(model, "public/templates/signup.vm")));
		
		post("/signup/insert", (request, response) -> userService.insert(request, response));
	}
}
