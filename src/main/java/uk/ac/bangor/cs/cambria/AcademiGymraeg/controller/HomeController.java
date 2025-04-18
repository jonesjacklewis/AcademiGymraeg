package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;


/**
 * @author jcj23xfb, cnb22xdk
 */

@Controller
public class HomeController {

	@Autowired
	UserRepository repo;
	
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
