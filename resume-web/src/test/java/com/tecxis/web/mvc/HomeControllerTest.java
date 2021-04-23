package com.tecxis.web.mvc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tecxis.web.mvc.HomeController;

import junit.framework.Assert;

public class HomeControllerTest {

	@Test
	public void testShowHomaPage() {
		
		HomeController controller = new HomeController();		
		Map <String, Object> model = new HashMap<>();	
		String viewName = controller.showHomePage(model);
		
		Assert.assertEquals("resume", viewName);
		Assert.assertEquals("This is some text", model.get("welcomeText"));
	}
	
	@Test
	public void testShowAngular() {
		
		HomeController controller = new HomeController();		
		Map <String, Object> model = new HashMap<>();	
		String viewName = controller.showAngular(model);
		
		Assert.assertEquals("angular", viewName);
		Assert.assertEquals("This is some text", model.get("welcomeText"));
	}

}
