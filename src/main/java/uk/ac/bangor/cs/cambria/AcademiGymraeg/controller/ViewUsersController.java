package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
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
	UserService userService; // Get logged in user details

	@Autowired
	UserRepository repo;

	public static String editConfirmationMessage = "";
	private static final Logger logger = LoggerFactory.getLogger(ViewUsersController.class);

	/**
	 * GET handler for the view users page
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
	@GetMapping
	public String viewUsers(Model m) {

		Long userId = userService.getLoggedInUserId();

		if (userId == null) {
			logger.debug("User ID is null");
			return "redirect:/home";
		}

		String forename = userService.getLoggedInUserForename();
		String email = userService.getLoggedInUserEmail();
		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		m.addAttribute("userId", userId);
		m.addAttribute("forename", forename);
		m.addAttribute("email", email);
		m.addAttribute("isAdmin", isAdmin);
		m.addAttribute("isInstructor", isInstructor);

		List<User> allUsers = repo.findAll();
		allUsers = allUsers.stream().filter(u -> u.getUserId() != userId).toList();

		m.addAttribute("allusers", allUsers);

		if (!editConfirmationMessage.isBlank()) {
			m.addAttribute("editconfirmationmessage", editConfirmationMessage);
			editConfirmationMessage = "";
		}

		return "viewusers";
	}

}
