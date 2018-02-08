package com.tecxis.portal.mvc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller
 **/
@Controller
public class HomeController {

	
		@RequestMapping({"/" , "/home"})
		public String showHomaPage(Map <String, Object> model) {
			model.put("welcomeText", "This is some text");
			
			
			/**Returns logical name of view*/
			return "index.html";
		}
}
