package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jcj23xfb
 */

@Controller
public class LoginController {
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
