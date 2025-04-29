package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jcj23xfb
 */

@Controller
public class LoginController {
	/**
	 * The GET handler for the login page
	 * 
	 * @return a {@link String} representation of an HTML template
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
