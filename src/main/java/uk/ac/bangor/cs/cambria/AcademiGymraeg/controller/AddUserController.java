package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * @author grs22lkc, jcj23xfb
 */

@Controller
public class AddUserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository repo;

	@Autowired
	ValidatorService validator;

	@Autowired
	PasswordEncoder encoder;

	public static String addConfirmationMessage = "";
	public static String addErrorMessage = "";
	private static final Logger logger = LoggerFactory.getLogger(AddUserController.class);

	/**
	 * 
	 * The GET handler for the add new user page
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
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

	/**
	 * 
	 * The POST handler for the add new user page
	 * 
	 * @param user       the {@link User} populated from the submitted form
	 * @param admin      a boolean indicating whether the new user should have admin
	 *                   privileges
	 * @param instructor a boolean indicating whether the new user should have
	 *                   instructor privileges
	 * @param model      a {@link Model} used to pass attributes to the view
	 * @return
	 */
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute User user,
			@RequestParam(required = false, defaultValue = "false") boolean admin,
			@RequestParam(required = false, defaultValue = "false") boolean instructor, Model model) {
		
		if(!validator.isValidEmail(user.getUsername())) {
			logger.debug("Invalid Email");
			addErrorMessage = "Unable to add user due to invalid email.";
			return "redirect:/addUser";
		}
		
		if(!validator.isValidPassword(user.getPassword())) {
			logger.debug("Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
			addErrorMessage = "Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";
			return "redirect:/addUser";
		}


		user.setAdmin(admin);
		user.setInstructor(instructor);

		user.setPassword(encoder.encode(user.getPassword()));

		repo.save(user);
		addConfirmationMessage = "User successfully added";
		return "redirect:/addUser";
	}

}
