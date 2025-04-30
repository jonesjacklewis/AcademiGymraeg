package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ResultsService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb
 */
public class ViewResultsControllerTest {
	private ViewResultsController controller;

	private UserService mockUserService;
	private TestRepository mockTestRepository;
	private UserRepository mockUserRepository;
	private ResultsService mockResultsService;

	private Model mockModel;

	@BeforeEach
	void setUp() {
		controller = new ViewResultsController();

		mockUserService = mock(UserService.class);
		mockTestRepository = mock(TestRepository.class);
		mockUserRepository = mock(UserRepository.class);
		mockResultsService = mock(ResultsService.class);
		mockModel = mock(Model.class);

		controller.userService = mockUserService;
		controller.testRepo = mockTestRepository;
		controller.userRepo = mockUserRepository;
		controller.rs = mockResultsService;
	}

	@Test
	void getHandler_nullUserId() {
		when(mockUserService.getLoggedInUserId()).thenReturn(null);

		String viewName = controller.viewResults(mockModel);

		assertEquals(viewName, "redirect:/home");

	}

	@Test
	void getHandler_emptyUser() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepository.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.viewResults(mockModel);

		assertEquals(viewName, "redirect:/home");

	}

	@Test
	void getHandler_success() {

		User user = new User();
		user.setUserId(1L);

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.viewResults(mockModel);

		assertEquals(viewName, "viewresults");

	}
}
