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
    private UserService userService; //Get logged in user details
	
	@GetMapping("/home")
    public String homePage(Model m) {
        // Retrieve individual user attributes
        Long userId = userService.getLoggedInUserId();
        String forename = userService.getLoggedInUserForename();
        String email = userService.getLoggedInUserEmail();
        boolean isAdmin = userService.isLoggedInUserAdmin();
        boolean isInstructor = userService.isLoggedInUserInstructor();

        // Add each attribute to the model separately
        m.addAttribute("userId", userId);
        m.addAttribute("forename", forename);
        m.addAttribute("email", email);
        m.addAttribute("isAdmin", isAdmin);
        m.addAttribute("isInstructor", isInstructor);

        return "home"; // Return the view
    }
	
	/**
	 * Sets root to the homepage when a user is logged in.
	 */
	@GetMapping("/")
	public String homeRoot() {
		return "home";
	}
	
}
