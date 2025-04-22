package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserAuthService;


/**
 * @author jcj23xfb, cnb22xdk
 */

@Controller
public class HomeController {

	@Autowired
    private UserAuthService userAuthService;
	
	@GetMapping("/home")
	public String homePage(Model m) {
		// Retrieve the logged-in user's email
        String email = null;
		try {
			email = UserAuthService.getUsername();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Add the email to the model
        m.addAttribute("email", email);
				
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
