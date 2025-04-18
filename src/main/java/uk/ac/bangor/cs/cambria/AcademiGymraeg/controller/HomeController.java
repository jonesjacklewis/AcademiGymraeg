package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.AcademiGymraegApplication;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> currentuser = repo.findByEmailAddress(auth.getName());
		
		m.addAttribute("currentuser", currentuser.get().getForename());
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
