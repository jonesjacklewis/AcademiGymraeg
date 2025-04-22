package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author ptg22svs
 */

/**
 * All requests to "/edituser" are handled by this controller
 */

@Controller
@RequestMapping("/edituser")
@PreAuthorize("hasRole('ADMIN')")
public class EditUserController {
	

	@Autowired
	private UserRepository repo;
		
@Autowired
	private ValidatorService validator;
	/**
	 * @param id - id of User object to add to the Springboot model
	 * @param m - Springboot model
	 * @return string indicating html file to serve
	 * @apiNote GET requests to "/edituser/{userID}
	 */
		@GetMapping("/{id}")
		public String userEditPage(@PathVariable("id") Long id, Model m) {

		Optional<User> userOptional = repo.findById(id);
		
		if(userOptional.isEmpty()) {
			return "redirect:/viewusers";
		}		
		
		if (!m.containsAttribute("user"))
			m.addAttribute("user", userOptional.get());
			
			
		
		
		return "useredit";
	}


		/**
		 * @param u - User object to edit
		 * @param result - Form submission result
		 * @param m - Springboot model
		 * @return string indicating redirect target
		 * @apiNote  POST requests to "/edituser"
		 */
	@PostMapping
	public String editUser(@Valid User u,  BindingResult result, Model m)  {

		if (result.hasErrors()) {
			m.addAttribute("user", u);
			return userEditPage(u.getUserId(), m);
		} else {

			var passwords = u.getPassword().split(",");
			
			
			
			if(!passwords[0].equals(passwords[1]))
				{
					result.rejectValue("password", "error.password", "The passwords do not match.");
					m.addAttribute("user", u);
					return userEditPage(u.getUserId(), m);
				}
			else if (!(validator.isValidEmail(u.getUsername()))) {
				result.rejectValue("username", "error.username", "The username was not a valid email address.");
					m.addAttribute("user", u);
					return userEditPage(u.getUserId(), m);
			}
			else if (!validator.isValidPassword(u.getPassword())) {
				result.rejectValue("password", "error.password", "Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
					m.addAttribute("user", u);
					return userEditPage(u.getUserId(), m);
			}

			repo.save(u);			
			
			 
			ViewUsersController.editConfirmationMessage = "Changes to user '" + u.getUsername() + "' were saved.";
			
			return "redirect:/viewusers";
		}
				

	}
	

}
