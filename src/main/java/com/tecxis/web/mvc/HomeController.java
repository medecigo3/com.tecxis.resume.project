package com.tecxis.web.mvc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller
 **/
@Controller
public class HomeController {

		/**Handles requests whose path is "/", "/home", "/index" */
		@RequestMapping({"/" , "/home", "/index", "/resume"})
		public String showHomePage(Map <String, Object> model) {
			model.put("welcomeText", "This is some text");
			
			
			/**Returns logical name of view*/
			return "resume";
		}
		
		/**Handles requests whose path is "/hello-angularjs" */
		@RequestMapping({"/hello-angularjs"})
		public String showAngular(Map <String, Object> model) {
			model.put("welcomeText", "This is some text");
			
			
			/**Returns logical name of view*/
			return "angular";
		}
}
