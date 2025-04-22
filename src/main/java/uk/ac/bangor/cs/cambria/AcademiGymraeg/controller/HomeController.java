package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * @author jcj23xfb, cnb22xdk
 */

@Controller
public class HomeController {

	
	@GetMapping("/home")
	public String homePage(Model m) {
		
		
				return "home";
	}
	
	/**
	 * Sets root to the homepage when a user is logged in.
	 */
	@GetMapping("/")
	public String homeRoot() {
		return "home";
	}
	
}
