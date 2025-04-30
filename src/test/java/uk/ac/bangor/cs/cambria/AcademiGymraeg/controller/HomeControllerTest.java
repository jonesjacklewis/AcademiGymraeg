package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author jcj23xfb
 */
public class HomeControllerTest {
	private HomeController controller;

	private UserService mockUserService;
	private Model mockModel;

	@BeforeEach
	void setUp() {
		controller = new HomeController();

		mockUserService = mock(UserService.class);
		mockModel = mock(Model.class);

		controller.userService = mockUserService;
	}

	@Test
	void getHandler_home() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserService.getLoggedInUserForename()).thenReturn("John");
		when(mockUserService.getLoggedInUserEmail()).thenReturn("john@mail.me");
		when(mockUserService.isLoggedInUserAdmin()).thenReturn(true);
		when(mockUserService.isLoggedInUserInstructor()).thenReturn(false);

		String viewName = controller.homePage(mockModel);

		assertEquals("home", viewName);

		verify(mockModel).addAttribute("userId", 1L);
		verify(mockModel).addAttribute("forename", "John");
		verify(mockModel).addAttribute("email", "john@mail.me");
		verify(mockModel).addAttribute("isAdmin", true);
		verify(mockModel).addAttribute("isInstructor", false);

	}
	
	@Test
	void getHandler_root() {
		String viewName = controller.homeRoot();

		assertEquals("redirect:/home", viewName);

	}
}
