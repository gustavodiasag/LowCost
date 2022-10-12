package app;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.*;

import java.util.HashMap;

public class Application {

	public static void main(String[] args) {
		
		port(6789);
		
		staticFiles.location("/public");
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		get("/", (request, response) -> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/index.vm")));
		
		get("/profile/", (request, response)-> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/profile.vm")));
		
		get("/services/", (request, response)-> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/services.vm")));
		
		get("/forum/", (request, response)-> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/forum.vm")));
		
		get("/about/", (request, response) -> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/about.vm")));
		
		get("/tables/", (request, response) -> new VelocityTemplateEngine().render(new ModelAndView(model, "public/templates/tables.vm")));
	}
}
