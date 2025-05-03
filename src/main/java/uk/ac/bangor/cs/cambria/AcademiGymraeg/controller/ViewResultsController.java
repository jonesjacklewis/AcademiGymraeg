package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Result;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ResultsService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb, grs22lkc
 */

@Controller
@RequestMapping("/viewResults")
public class ViewResultsController {

	@Autowired
	UserService userService; // Get logged in user details

	@Autowired
	TestRepository testRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ResultsService rs;

	public static String editConfirmationMessage = "";
	private static final Logger logger = LoggerFactory.getLogger(ViewResultsController.class);

	/**
	 * GET handler for the view results page
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
	@GetMapping
	public String viewResults(Model m) {

		Long userId = userService.getLoggedInUserId();

		if (userId == null) {
			logger.debug("User ID is null");
			return "redirect:/home";
		}

		Optional<User> optionalUser = userRepo.findById(userId);

		if (optionalUser.isEmpty()) {
			logger.debug("No user for id: " + userId.toString());
			return "redirect:/home";
		}

		User user = optionalUser.get();

		String forename = userService.getLoggedInUserForename();
		String email = userService.getLoggedInUserEmail();
		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		m.addAttribute("userId", userId);
		m.addAttribute("forename", forename);
		m.addAttribute("email", email);
		m.addAttribute("isAdmin", isAdmin);
		m.addAttribute("isInstructor", isInstructor);

		List<Test> tests = testRepo.findAllByUser(user);

		// Filters out incomplete tests due to how the EPOCH is used - jcj23xfb
		tests = tests.stream().filter(t -> t.getStartDateTime().isBefore(t.getEndDateTime())).toList();

		List<Result> results = rs.convertTestsToResults(tests);
		
		results = results.stream().sorted((r1, r2) -> r2.getStartDateTime().compareTo(r1.getStartDateTime())).toList();
		
		m.addAttribute("allresults", results);

		return "viewresults";
	}

}
