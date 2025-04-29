package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	TestRepository testRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	TestConfigurer testConfig;

	@Autowired
	UserService userService;

	private List<Question> questions;
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/**
	 * GET handler for the test page
	 * 
	 * @param m a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
	@GetMapping({ "/test" })
	public String takeTest(Model model, RedirectAttributes redirectAttributes) {

		Long id = userService.getLoggedInUserId();

		if (id == null) {
			logger.debug("User ID is null");
			return "redirect:/home";
		}

		Optional<User> optionalUser = userRepo.findById(id);

		if (optionalUser.isEmpty()) {
			logger.debug("No user for id: " + id.toString());
			return "redirect:/home";
		}

		User user = optionalUser.get();

		if (!user.canStartNewTest()) {
			Instant nextTestTime = user.getNextTestStartTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
					.withZone(ZoneId.systemDefault());
			String formattedTime = formatter.format(nextTestTime);

			redirectAttributes.addFlashAttribute("errorMessage",
					"You are not allowed to start a new test until: " + formattedTime);
			logger.debug("User is unable to start a new test");
			return "redirect:/home";
		}

		Instant now = Instant.now();
		user.setTestStartTimetamp(now);

		userRepo.save(user);

		boolean isAdmin = userService.isLoggedInUserAdmin();
		boolean isInstructor = userService.isLoggedInUserInstructor();

		int numberOfQuestions = 20;

		Test test = new Test(user, ZonedDateTime.now(), numberOfQuestions);

		testRepo.save(test);

		testConfig.generateQuestionsForTest(test);

		testRepo.save(test);

		questions = questionRepo.findAllByTest(test);

		model.addAttribute("test", test);
		model.addAttribute("questions", questions);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("isInstructor", isInstructor);

		return "test";
	}

	/**
	 * @param testFromForm - a {@link Test} object to modify
	 * @param m            a {@link Model} used to pass attributes to the view
	 * @return a {@link String} representation of an HTML template
	 */
	@PostMapping({ "/submitTest" })
	public String submitTest(@ModelAttribute("test") Test testFromForm, Model model) {

		Long id = userService.getLoggedInUserId();

		if (id == null) {
			logger.debug("User ID is null");
			return "home";
		}

		Optional<User> optionalUser = userRepo.findById(id);

		if (optionalUser.isEmpty()) {
			logger.debug("No user for id: " + id.toString());
			return "home";
		}

		User user = optionalUser.get();

		Long testId = testFromForm.getTestId();

		if (testId == null) {
			logger.error("No test ID provided");
			return "redirect:/home";
		}

		Optional<Test> optionalTest = testRepo.findById(testId);

		if (optionalTest.isEmpty()) {
			logger.debug("No test for id: " + testId.toString());
			return "redirect:/home";
		}

		Test test = optionalTest.get();

		int score = 0;

		List<Question> submittedQuestions = testFromForm.getQuestions();

		// converts the submittedQuestions to a Map where key is the question ID and the
		// value is the question - jcj23xfb
		Map<Long, Question> fullQuestions = submittedQuestions.stream().filter(q -> q.getQuestionId() != null)
				.map(q -> questionRepo.findById(q.getQuestionId())).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toMap(Question::getQuestionId, Function.identity()));

		for (Question question : submittedQuestions) {

			Question full = fullQuestions.getOrDefault(question.getQuestionId(), null);

			if (full == null) {
				logger.debug("Question ID not found");
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

		// EPOCH is used as a date to show user hasn't got a valid ongoing test:
		// jcj23xfb
		user.setTestStartTimetamp(Instant.EPOCH);
		userRepo.save(user);

		return "redirect:/viewResults";

	}

}
