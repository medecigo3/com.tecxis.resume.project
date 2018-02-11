package com.tecxis.portal.mvc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

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

}
