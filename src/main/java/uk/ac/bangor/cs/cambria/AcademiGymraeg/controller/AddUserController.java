package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author grs22lkc
 */

@Controller
public class AddUserController {

	@Autowired
	private UserRepository repo;

	@Autowired
	private ValidatorService validator;

	public static String addConfirmationMessage = "";
	public static String addErrorMessage = "";

	@GetMapping("/addUser")
	public String addUserPage(Model m) {

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
