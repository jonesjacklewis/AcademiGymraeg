package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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
    private UserService userService; //Get logged in user details

	@Autowired
	private TestRepository testRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ResultsService rs;

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
		
		Optional<User> optionalU = userRepo.findById(userId);
		
		if(optionalU.isEmpty()) {
			return "home";
		}
		
		User u = optionalU.get();
				
		List<Test> tests = testRepo.findAllByUser(u);
		
		tests = tests.stream().filter(t -> t.getStartDateTime().isBefore(t.getEndDateTime())).toList();
		
		List<Result> results = rs.convertTestsToResults(tests);

		m.addAttribute("allresults", results);

		return "viewresults";
	}

}
