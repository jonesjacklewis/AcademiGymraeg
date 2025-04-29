package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb
 */
public class ViewUsersControllerTest {
	ViewUsersController controller;

	private UserService mockUserService;
	private UserRepository mockUserRepository;

	private Model mockModel;

	@BeforeEach
	void setUp() {
		controller = new ViewUsersController();

		mockUserService = mock(UserService.class);
		mockUserRepository = mock(UserRepository.class);
		mockModel = mock(Model.class);

		controller.userService = mockUserService;
		controller.repo = mockUserRepository;
	}

	@Test
	void getHandler_nullUserId() {
		when(mockUserService.getLoggedInUserId()).thenReturn(null);

		String viewName = controller.viewUsers(mockModel);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void getHandler_success() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);

		String viewName = controller.viewUsers(mockModel);

		assertEquals("viewusers", viewName);
	}
}
