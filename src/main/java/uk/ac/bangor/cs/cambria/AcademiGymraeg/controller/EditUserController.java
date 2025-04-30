package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.transaction.Transactional;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.EditUserDTO;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author ptg22svs, jcj23xfb
 */

/**
 * All requests to "/edituser" are handled by this controller
 */

@Controller
@RequestMapping("/edituser")
@PreAuthorize("hasRole('ADMIN')")
public class EditUserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	TestRepository testRepo;

	@Autowired
	ValidatorService validator;
	private static final Logger logger = LoggerFactory.getLogger(EditUserController.class);

	/**
	 * @param id - {@link Long} id of User object to add to the Springboot model
	 * @param m  - a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 * @apiNote GET requests to "/edituser/{userID}
	 */
	@GetMapping("/{id}")
	public String userEditPage(@PathVariable Long id, Model m) {

		Optional<User> userOptional = userRepo.findById(id);

		if (userOptional.isEmpty()) {
			logger.debug("User does not exist");
			return "redirect:/viewusers";
		}

		User user = userOptional.get();

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(user.getUserId());
		dto.setForename(user.getForename());
		dto.setUsername(user.getUsername());
		dto.setAdmin(user.isAdmin());
		dto.setInstructor(user.isInstructor());

		if (!m.containsAttribute("editUserDTO"))
			m.addAttribute("editUserDTO", dto);

		return "useredit";
	}

	/**
	 * @param u      - a {@link User} object to edit
	 * @param result - Form submission result as a {@link BindingResult}
	 * @param m      - a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 * @apiNote POST requests to "/edituser"
	 */
	@PostMapping
	public String editUser(@ModelAttribute("editUserDTO") EditUserDTO dto, BindingResult result, Model m) {

		if (result.hasErrors()) {
			m.addAttribute("user", dto);
			logger.error("Unable to edit the user");
			return userEditPage(dto.getUserId(), m);
		}

		if (!validator.isValidEmail(dto.getUsername(), true)) {
			result.rejectValue("username", "error.username", "The username was not a valid email address.");
			m.addAttribute("user", dto);
			return userEditPage(dto.getUserId(), m);
		}

		boolean editedPassword = false;

		if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
			if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
				result.rejectValue("password", "error.newPassword", "The passwords do not match.");
				m.addAttribute("user", dto);
				return userEditPage(dto.getUserId(), m);
			}

			if (!validator.isValidPassword(dto.getNewPassword())) {
				result.rejectValue("password", "error.newPassword",
						"Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
				m.addAttribute("user", dto);
				return userEditPage(dto.getUserId(), m);
			}

			editedPassword = true;
		}

		Optional<User> optionalUser = userRepo.findById(dto.getUserId());

		if (optionalUser.isEmpty()) {
			return "redirect:/home";
		}

		User user = optionalUser.get();

		user.setForename(dto.getForename());
		user.setAdmin(dto.isAdmin());
		user.setInstructor(dto.isInstructor());
		user.setUsername(dto.getUsername());

		if (editedPassword) {
			user.setPassword(dto.getConfirmPassword());
		}

		userRepo.save(user);

		ViewUsersController.editConfirmationMessage = "Changes to user '" + user.getUsername() + "' were saved.";

		return "redirect:/viewusers";

	}

	/**
	 * 
	 * Deletes the user by ID
	 * 
	 * @param id - {@link Long} id of User object to delete
	 * @return a {@link String} representation of an HTML template
	 */
	@Transactional
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {

		if (id == null) {
			logger.debug("User ID null");
			return "redirect:/viewusers";
		}

		Optional<User> userOptional = userRepo.findById(id);

		if (userOptional.isEmpty()) {
			logger.debug("User does not exist");
			return "redirect:/viewusers";
		}

		User user = userOptional.get();

		testRepo.deleteAllByUser(user);
		userRepo.delete(user);

		return "redirect:/viewusers";
	}
}
