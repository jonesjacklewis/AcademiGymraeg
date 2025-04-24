package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Arrays;
import java.util.List;
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
import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author ptg22svs, cnb22xdk
 */

/**
 * All requests to "/noun" (and subdocs) are handled by this controller
 */
@Controller
@RequestMapping("/noun")
public class NounController {
	
	@Autowired
    private UserService userService; //Get logged in user details
	
	@Autowired
	private NounRepository repo;

	private List<Gender> genders = Arrays.asList(Gender.values());
	
	public static String addConfirmationMessage = "";
	
	public static String editConfirmationMessage = "";
	
	public static String deleteConfirmationMessage = "";

	
	/**
	 * @param m - Springboot model
	 * @return string indicating html file to serve
	 * @apiNote GET requests to "/noun" (Initial request)
	 */
	@GetMapping
	@PreAuthorize("hasRole('INSTRUCTOR')" + " or hasRole('ADMIN')")
	public String nounAdminPage(Model m) {

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
		
		if (!m.containsAttribute("noun"))
			m.addAttribute("noun", new Noun());
		
		if(!addConfirmationMessage.isBlank())
		{
			m.addAttribute("addconfirmationmessage", addConfirmationMessage);
			addConfirmationMessage = "";
		}
		else if(!editConfirmationMessage.isBlank())
		{
			m.addAttribute("editconfirmationmessage", editConfirmationMessage);
			editConfirmationMessage = "";
		}
		else if(!deleteConfirmationMessage.isBlank())
		{
			m.addAttribute("deleteconfirmationmessage", deleteConfirmationMessage);
			deleteConfirmationMessage = "";
		}
		
		
		m.addAttribute("genders", genders);

		m.addAttribute("allnouns", repo.findAll());

		return "nounadmin";
	}

	
	/**
	 * @param n - Noun object to add to the datastore
	 * @param result - Form submission result
	 * @param m - Springboot model
	 * @return string indicating redirect target
	 * @apiNote POST requests to "/noun"
	 */
	@PostMapping
	@PreAuthorize("hasRole('INSTRUCTOR')" + " or hasRole('ADMIN')")
	public String newNoun(@Valid Noun n, BindingResult result, Model m)  {

		if (result.hasErrors()) {
			m.addAttribute("noun", n);
			return nounAdminPage(m);
		} else {

			
	
			n.setEnglishNoun(n.getEnglishNoun().toLowerCase());
			n.setWelshNoun(n.getWelshNoun().toLowerCase());

			repo.save(n);			
			addConfirmationMessage = "New noun '"+ n.getWelshNoun() + " | " + n.getEnglishNoun() + "' added.";
			return "redirect:/noun";
		}
		
		

	}

	
	/**
	 * @param id - Id of Noun that will be deleted
	 * @return string indicating redirect target
	 * @apiNote GET requests to "/noun/deletenoun/{nounID}
	 */
	@GetMapping("/deletenoun/{id}")
	@PreAuthorize("hasRole('INSTRUCTOR')" + " or hasRole('ADMIN')")
	public String deletenoun(@PathVariable("id") Long id) {
		Noun nounToDelete = repo.findById(id).get();
		deleteConfirmationMessage = "Noun '" + nounToDelete.getWelshNoun() + " | " + nounToDelete.getEnglishNoun() + "' was deleted.";
		repo.deleteById(id);
		return "redirect:/noun";
	}

	
	/**
	 * @param noun - Noun object to edit
	 * @param result - Form submission result
	 * @param m - Springboot model
	 * @return string indicating redirect target
	 * @apiNote  POST requests to "/noun/editnoun"
	 */
	@PostMapping("/editnoun")
	@PreAuthorize("hasRole('INSTRUCTOR')" + " or hasRole('ADMIN')")
	public String editnoun(@Valid Noun noun, BindingResult result, Model m) {

		if (result.hasErrors()) {
			m.addAttribute("noun", noun);
			return nounEditPage(noun.getNounId(), m);
		} else {
			
			Noun originalNoun = repo.findById(noun.getNounId()).get();
			
			
			noun.setEnglishNoun(noun.getEnglishNoun().toLowerCase());
			noun.setWelshNoun(noun.getWelshNoun().toLowerCase());

			repo.save(noun);
			
			editConfirmationMessage = "Changes to noun were saved.";
			
			m.addAttribute("noun", new Noun());

			return "redirect:/noun";
		}

	}


	/**
	 * @param id - id of Noun object to add to the Springboot model
	 * @param m - Springboot model
	 * @return string indicating html file to serve
	 * @apiNote GET requests to "/noun/editnoun/{nounID}
	 */
	@GetMapping("/editnoun/{id}")
	@PreAuthorize("hasRole('INSTRUCTOR')" + " or hasRole('ADMIN')")
	public String nounEditPage(@PathVariable("id") Long id, Model m) {

	
		Optional<Noun> nounOptional = repo.findById(id);
		
		if(nounOptional.isEmpty()) {
			return "redirect:/noun";
		}		
		
		if (!m.containsAttribute("noun"))
			m.addAttribute("noun", nounOptional.get());
		
		m.addAttribute("genders", genders);

		return "nounedit";
	}

}
