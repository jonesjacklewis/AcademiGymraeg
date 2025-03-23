package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jcj23xfb
 */

@Controller
public class HomeController {
	@GetMapping("/home")
	public String homePage() {
		return "home";
	}
}
