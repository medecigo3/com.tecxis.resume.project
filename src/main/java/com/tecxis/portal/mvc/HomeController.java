package com.tecxis.portal.mvc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller
 **/
@Controller
public class HomeController {

		/**Handles requests whose path is "/", "/home", "/index" */
		@RequestMapping({"/" , "/home", "/index", })
		public String showHomaPage(Map <String, Object> model) {
			model.put("welcomeText", "This is some text");
			
			
			/**Returns logical name of view*/
			return "index";
		}
}
