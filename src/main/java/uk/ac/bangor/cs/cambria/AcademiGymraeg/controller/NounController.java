package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.NounService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author ptg22svs, cnb22xdk, grs22lkc
 */

/**
 * All requests to "/noun" (and subdocs) are handled by this controller
 */
@Controller
@RequestMapping("/noun")
public class NounController {

	@Autowired
	UserService userService; // Get logged in user details

	@Autowired
	NounService nounService;

	private List<Gender> genders = Arrays.asList(Gender.values());

	public static String addConfirmationMessage = "";

	public static String editConfirmationMessage = "";

	public static String deleteConfirmationMessage = "";
	private static final Logger logger = LoggerFactory.getLogger(NounController.class);

	/**
	 * 
	 * Sets a message property that will be returned to the HTML form
	 * 
	 * @param message a {@link String} message to show
	 * @param type    the type of message as a {@link String}
	 */
	static void setMessage(String message, String type) {
		if (message == null || type == null) {
			logger.error("No message or type provided");
			throw new IllegalArgumentException("Null argument passed");
		}

		switch (type) {
		case "add":
			addConfirmationMessage = message;
			break;
		case "edit":
			editConfirmationMessage = message;
			break;
		case "delete":
			deleteConfirmationMessage = message;
			break;
		}

	}

	/**
	 * Resets the potential message types
	 */
	static void clearMessages() {
		addConfirmationMessage = "";
		editConfirmationMessage = "";
		deleteConfirmationMessage = "";
	}

	/**
	 * The GET Handler for Noun Controller
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 * @apiNote GET requests to "/noun" (Initial request)
	 */
	@GetMapping
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public String nounAdminPage(Model m) {

		// Retrieve individual user attributes
		Long userId = userService.getLoggedInUserId();
		String forename = userService.getLoggedInUserForename();
		String email = userService.getLoggedInUserEmail();
		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		if (userId == null) {
			logger.error("Logged in user details are not present");
			throw new IllegalArgumentException("Unable to get logged in user");
		}

		m.addAttribute("userId", userId);
		m.addAttribute("forename", forename);
		m.addAttribute("email", email);
		m.addAttribute("isAdmin", isAdmin);
		m.addAttribute("isInstructor", isInstructor);

		if (!m.containsAttribute("noun"))
			m.addAttribute("noun", new Noun());

		m.addAttribute("addconfirmationmessage", addConfirmationMessage);
		m.addAttribute("editconfirmationmessage", editConfirmationMessage);
		m.addAttribute("deleteconfirmationmessage", deleteConfirmationMessage);
		clearMessages();

		m.addAttribute("genders", genders);

		m.addAttribute("allnouns", nounService.getAllNouns());

		return "nounadmin";
	}

	/**
	 * @param n      - a {@link Noun} object to add to the datastore
	 * @param result - Form submission result as a {@link BindingResult}
	 * @param m      a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 * @apiNote POST requests to "/noun"
	 */
	@PostMapping
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public String newNoun(@Valid Noun n, BindingResult result, Model m) {

		if (n == null) {
			logger.error("Null noun argument");
			throw new IllegalArgumentException("Null Noun argument");
		}

		if (result.hasErrors()) {
			logger.error("Result contains errors");
			m.addAttribute("noun", n);
			return nounAdminPage(m);
		}

		n.setEnglishNoun(n.getEnglishNoun().toLowerCase());
		n.setWelshNoun(n.getWelshNoun().toLowerCase());

		nounService.saveRecord(n);
		setMessage("New noun '" + n.getWelshNoun() + " | " + n.getEnglishNoun() + "' added.", "add");
		return "redirect:/noun";

	}

	/**
	 * @param id - Id of Noun that will be deleted
	 * @return string indicating redirect target
	 * @apiNote GET requests to "/noun/deletenoun/{nounID}
	 */
	@GetMapping("/deletenoun/{id}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public String deleteNoun(@PathVariable Long id) {

		if (id == null) {
			throw new IllegalArgumentException("Null id argument");
		}

		Optional<Noun> nounToDelete = nounService.getById(id);

		if (nounToDelete.isEmpty()) {
			throw new IllegalArgumentException("Unknown Noun Id");
		}

		setMessage("Noun '" + nounToDelete.get().getWelshNoun() + " | " + nounToDelete.get().getEnglishNoun()
				+ "' was deleted.", "delete");
		nounService.deleteById(id);
		return "redirect:/noun";
	}

	/**
	 * @param noun   - Noun object to edit
	 * @param result - Form submission result
	 * @param m      - Springboot model
	 * @return string indicating redirect target
	 * @apiNote POST requests to "/noun/editnoun"
	 */
	@PostMapping("/editnoun")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public String editNoun(@Valid Noun noun, BindingResult result, Model m) {

		if (noun == null) {
			throw new IllegalArgumentException("Null Noun argument");
		}

		if (result.hasErrors()) {
			m.addAttribute("noun", noun);
			return nounEditPage(noun.getNounId(), m);
		}

		noun.setEnglishNoun(noun.getEnglishNoun().toLowerCase());
		noun.setWelshNoun(noun.getWelshNoun().toLowerCase());

		nounService.saveRecord(noun);

		setMessage("Changes to noun were saved.", "edit");

		m.addAttribute("noun", new Noun());

		return "redirect:/noun";

	}

	/**
	 * @param id - id of Noun object to add to the Springboot model
	 * @param m  - Springboot model
	 * @return string indicating html file to serve
	 * @apiNote GET requests to "/noun/editnoun/{nounID}
	 */
	@GetMapping("/editnoun/{id}")
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public String nounEditPage(@PathVariable Long id, Model m) {

		if (id == null) {
			throw new IllegalArgumentException("Null id argument");
		}

		Optional<Noun> nounOptional = nounService.getById(id);

		if (nounOptional.isEmpty()) {
			throw new IllegalArgumentException("Unknown Noun Id");
		}

		if (!m.containsAttribute("noun"))
			m.addAttribute("noun", nounOptional.get());

		m.addAttribute("genders", genders);

		return "nounedit";
	}

}
