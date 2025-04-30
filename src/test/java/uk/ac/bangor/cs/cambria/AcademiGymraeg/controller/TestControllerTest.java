package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.TestConfigurer;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb
 */
public class TestControllerTest {

	private TestController controller;

	private TestRepository mockTestRepo;
	private UserRepository mockUserRepo;
	private QuestionRepository mockQuestionRepo;
	private TestConfigurer mockTestConfigurer;
	private UserService mockUserService;

	private Model mockModel;
	private RedirectAttributes mockRedirectAttributes;

	@BeforeEach
	void setUp() {
		mockTestRepo = mock(TestRepository.class);
		mockUserRepo = mock(UserRepository.class);
		mockQuestionRepo = mock(QuestionRepository.class);
		mockTestConfigurer = mock(TestConfigurer.class);
		mockUserService = mock(UserService.class);

		mockModel = mock(Model.class);
		mockRedirectAttributes = mock(RedirectAttributes.class);

		controller = new TestController();

		controller.testRepo = mockTestRepo;
		controller.userRepo = mockUserRepo;
		controller.questionRepo = mockQuestionRepo;
		controller.testConfig = mockTestConfigurer;
		controller.userService = mockUserService;
	}

	@Test
	void getHandler_nullUserId() {
		when(mockUserService.getLoggedInUserId()).thenReturn(null);

		String viewName = controller.takeTest(mockModel, mockRedirectAttributes);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void getHandler_userDoesNotExist() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.takeTest(mockModel, mockRedirectAttributes);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void getHandler_userCannotStartTest() {

		User user = new User();
		user.setUserId(1L);
		user.setTestStartTimetamp(Instant.now().minus(5, ChronoUnit.MINUTES));

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.takeTest(mockModel, mockRedirectAttributes);

		verify(mockRedirectAttributes).addFlashAttribute(eq("errorMessage"), any(String.class));

		assertEquals("redirect:/home", viewName);

	}

	@Test
	void getHandler_success() {

		User user = new User();
		user.setUserId(1L);
		user.setTestStartTimetamp(Instant.EPOCH);

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.takeTest(mockModel, mockRedirectAttributes);

		verify(mockTestRepo, times(2)).save(any());

		assertEquals("test", viewName);

	}

	@Test
	void postHandler_nullUserId() {
		when(mockUserService.getLoggedInUserId()).thenReturn(null);

		String viewName = controller.submitTest(null, mockModel);

		assertEquals("home", viewName);
	}

	@Test
	void postHandler_userDoesNotExist() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.submitTest(null, mockModel);

		assertEquals("home", viewName);
	}

	@Test
	void postHandler_testIdNull() {
		User user = new User();
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test test = new uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test();
		test.setTestId(null);

		String viewName = controller.submitTest(test, mockModel);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void postHandler_testNotInDatabase() {
		User user = new User();

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));
		when(mockTestRepo.findById(1L)).thenReturn(Optional.empty());

		uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test test = new uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test();
		test.setTestId(1L);

		String viewName = controller.submitTest(test, mockModel);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void postHandler_success() {
		User user = new User();

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test test = new uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test();
		test.setTestId(1L);
		test.setQuestions(new ArrayList<Question>());

		when(mockTestRepo.findById(1L)).thenReturn(Optional.of(test));

		String viewName = controller.submitTest(test, mockModel);

		assertEquals("redirect:/viewResults", viewName);
	}

}
