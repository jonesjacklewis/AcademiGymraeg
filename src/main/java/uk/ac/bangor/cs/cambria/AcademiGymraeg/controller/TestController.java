package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.TestConfigurer;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author dwp22pzv, jcj23xfb, cnb22xdk
 */

@Controller
public class TestController {

	@Autowired
	private TestRepository testRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private TestConfigurer testConfig;

	@Autowired
	private UserService userService; // Get logged in user details

	private List<Question> questions;

	/**
	 * Called when the Take Test button on the homepage is pressed.
	 * 
	 * @param model
	 * @return String html template to render ("test")
	 */
	@GetMapping({ "/test" })
	public String takeTest(Model model) {

		Long id = userService.getLoggedInUserId();

		if (id == null) {
			return "home";
		}

		Optional<User> user = userRepo.findById(id);

		if (user.isEmpty()) {
			return "home";
		}

		User currentUser = user.get(); // Need to handle a missing user, but waiting until we decide on how to handle
										// getting the current user.

		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		int numberOfQuestions = 20; // Did we plan on storing the default number of questions somewhere else?

		Test test = new Test(currentUser, ZonedDateTime.now(), numberOfQuestions);

		testRepo.save(test);

		testConfig.generateQuestionsForTest(test);

		testRepo.save(test);

		questions = questionRepo.findAllByTest(test);

		model.addAttribute("test", test);
		model.addAttribute("questions", questions);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("isInstructor", isInstructor);

		return "test"; // Return the name of the HTML template to render
	}

	@PostMapping({ "/submitTest" })
	public String submitTest(@ModelAttribute("test") Test testFromForm, Model model) {

		Long testId = testFromForm.getTestId();

		if (testId == null) {
			return "redirect:/home";
		}

		Optional<Test> optionalTest = testRepo.findById(testId);

		if (optionalTest.isEmpty()) {
			return "redirect:/home";
		}

		Test test = optionalTest.get();

		int score = 0;

		List<Question> submittedQuestions = testFromForm.getQuestions();

		Map<Long, Question> fullQuestions = submittedQuestions.stream().filter(q -> q.getQuestionId() != null)
				.map(q -> questionRepo.findById(q.getQuestionId())).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toMap(Question::getQuestionId, Function.identity()));

		for (Question question : submittedQuestions) {

			Question full = fullQuestions.getOrDefault(question.getQuestionId(), null);

			if (full == null) {
				continue;
			}

			full.setGivenAnswer(question.getGivenAnswer());

			if (full.checkAnswer()) {
				score = score + 1;
			}

		}

		test.setNumberCorrect(score);

		test.setEndDateTime(ZonedDateTime.now());

		testRepo.save(test);

		return "redirect:/viewResults";

	}

}
