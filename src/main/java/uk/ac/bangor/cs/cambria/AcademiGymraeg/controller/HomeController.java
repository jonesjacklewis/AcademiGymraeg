package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb, cnb22xdk, grs22lkc
 */

@Controller
public class HomeController {

	@Autowired
	UserService userService; // Get logged in user details

	/**
	 * 
	 * The GET handler for the home page
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
	@GetMapping("/home")
	public String homePage(Model m) {
		Long userId = userService.getLoggedInUserId();
		String forename = userService.getLoggedInUserForename();
		String email = userService.getLoggedInUserEmail();
		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		m.addAttribute("userId", userId);
		m.addAttribute("forename", forename);
		m.addAttribute("email", email);
		m.addAttribute("isAdmin", isAdmin);
		m.addAttribute("isInstructor", isInstructor);

		return "home"; // Return the view
	}

	/**
	 * Redirects the root URL ("/") to the home page ("/home") after login.
	 * 
	 * @return a redirect to the home page
	 */
	@GetMapping("/")
	public String homeRoot() {
		return "redirect:/home";
	}

}
