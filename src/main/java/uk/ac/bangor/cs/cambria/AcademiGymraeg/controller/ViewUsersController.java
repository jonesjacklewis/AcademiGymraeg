package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author thh21bgf, grs22lkc
 */

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/viewusers")
public class ViewUsersController {

	@Autowired
    private UserService userService; //Get logged in user details
	
	@Autowired
	private UserRepository repo;
	
	public static String editConfirmationMessage = "";

	@GetMapping
	public String viewUsers(Model m) {
		
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
		
		m.addAttribute("allusers", repo.findAll());
		
		if(!editConfirmationMessage.isBlank())
		{
			m.addAttribute("editconfirmationmessage", editConfirmationMessage);
			editConfirmationMessage = "";
		}
		
		return "viewusers";
	}

}
