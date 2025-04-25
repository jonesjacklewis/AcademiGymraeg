package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author grs22lkc
 */

@Controller
public class AddUserController {

	@Autowired
    private UserService userService; //Get logged in user details
	
	@Autowired
	private UserRepository repo;

	@Autowired
	private ValidatorService validator;

	public static String addConfirmationMessage = "";
	public static String addErrorMessage = "";

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/addUser")
	public String addUserPage(Model m) {

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
		
		if (!addConfirmationMessage.isBlank()) {
			m.addAttribute("addconfirmationmessage", addConfirmationMessage);
			addConfirmationMessage = "";
		}

		if (!addErrorMessage.isBlank()) {
			m.addAttribute("addErrorMessage", addErrorMessage);
			addErrorMessage = "";
		}

		return "add-user";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute User user,
			@RequestParam(value = "admin", required = false, defaultValue = "false") boolean admin,
			@RequestParam(value = "instructor", required = false, defaultValue = "false") boolean instructor,
			Model model) {

		if (!(validator.isValidEmail(user.getUsername()) && validator.isValidPassword(user.getPassword()))) {
			addErrorMessage = "Unable to add user";
			return "redirect:/addUser";
		}

		user.setAdmin(admin);
		user.setInstructor(instructor);
		repo.save(user);
		addConfirmationMessage = "User successfully added";
		return "redirect:/addUser";
	}

}
