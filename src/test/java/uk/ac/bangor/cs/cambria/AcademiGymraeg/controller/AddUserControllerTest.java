package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author jcj23xfb
 */

public class AddUserControllerTest {

	private AddUserController controller;

	private UserService mockUserService;
	private UserRepository mockRepo;
	private ValidatorService mockValidator;
	private PasswordEncoder mockEncoder;
	private Model mockModel;

	@BeforeEach
	void setUp() {
		mockUserService = mock(UserService.class);
		mockRepo = mock(UserRepository.class);
		mockValidator = mock(ValidatorService.class);
		mockEncoder = mock(PasswordEncoder.class);
		mockModel = mock(Model.class);

		controller = new AddUserController();

		controller.userService = mockUserService;
		controller.repo = mockRepo;
		controller.validator = mockValidator;
		controller.encoder = mockEncoder;
	}

	@Test
	void getHandler_basicAttributes() {

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserService.getLoggedInUserForename()).thenReturn("John");
		when(mockUserService.getLoggedInUserEmail()).thenReturn("john@example.com");
		when(mockUserService.isLoggedInUserAdmin()).thenReturn(true);
		when(mockUserService.isLoggedInUserInstructor()).thenReturn(false);

		String viewName = controller.addUserPage(mockModel);

		assertEquals("add-user", viewName);

		verify(mockModel).addAttribute("userId", 1L);
		verify(mockModel).addAttribute("forename", "John");
		verify(mockModel).addAttribute("email", "john@example.com");
		verify(mockModel).addAttribute("isAdmin", true);
		verify(mockModel).addAttribute("isInstructor", false);

	}

	@Test
	void getHandler_withConfirmation() {

		AddUserController.addConfirmationMessage = "User Added!";

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserService.getLoggedInUserForename()).thenReturn("John");
		when(mockUserService.getLoggedInUserEmail()).thenReturn("john@example.com");
		when(mockUserService.isLoggedInUserAdmin()).thenReturn(true);
		when(mockUserService.isLoggedInUserInstructor()).thenReturn(false);

		String viewName = controller.addUserPage(mockModel);

		assertEquals("add-user", viewName);

		verify(mockModel).addAttribute("userId", 1L);
		verify(mockModel).addAttribute("forename", "John");
		verify(mockModel).addAttribute("email", "john@example.com");
		verify(mockModel).addAttribute("isAdmin", true);
		verify(mockModel).addAttribute("isInstructor", false);

		verify(mockModel).addAttribute("addconfirmationmessage", "User Added!");

		assertEquals("", AddUserController.addConfirmationMessage);

	}

	@Test
	void getHandler_withErrorMessage() {

		AddUserController.addErrorMessage = "User Fail!";

		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockUserService.getLoggedInUserForename()).thenReturn("John");
		when(mockUserService.getLoggedInUserEmail()).thenReturn("john@example.com");
		when(mockUserService.isLoggedInUserAdmin()).thenReturn(true);
		when(mockUserService.isLoggedInUserInstructor()).thenReturn(false);

		String viewName = controller.addUserPage(mockModel);

		assertEquals("add-user", viewName);

		verify(mockModel).addAttribute("userId", 1L);
		verify(mockModel).addAttribute("forename", "John");
		verify(mockModel).addAttribute("email", "john@example.com");
		verify(mockModel).addAttribute("isAdmin", true);
		verify(mockModel).addAttribute("isInstructor", false);

		verify(mockModel).addAttribute("addErrorMessage", "User Fail!");

		assertEquals("", AddUserController.addErrorMessage);

	}

	@Test
	void postHandler_withInvalidEmail() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("Password123!");

		when(mockValidator.isValidEmail(user.getUsername())).thenReturn(false);
		when(mockValidator.isValidPassword(user.getPassword())).thenReturn(true);

		String viewName = controller.addUser(user, false, true, mockModel);

		assertEquals("redirect:/addUser", viewName);
		assertEquals("Unable to add user due to invalid email.", AddUserController.addErrorMessage);

		verifyNoInteractions(mockRepo);
	}

	@Test
	void postHandler_withInvalidPassword() {
		User user = new User();
		user.setUsername("admin@mail.com");
		user.setPassword("Password123");

		when(mockValidator.isValidEmail(user.getUsername())).thenReturn(true);
		when(mockValidator.isValidPassword(user.getPassword())).thenReturn(false);

		String viewName = controller.addUser(user, false, true, mockModel);

		assertEquals("redirect:/addUser", viewName);
		assertEquals("Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.", AddUserController.addErrorMessage);

		verifyNoInteractions(mockRepo);
	}

	@Test
	void postHandler_withValidUser() {
		User user = new User();
		user.setUsername("admin@mail.com");
		user.setPassword("Password123!");

		when(mockValidator.isValidEmail(user.getUsername())).thenReturn(true);
		when(mockValidator.isValidPassword(user.getPassword())).thenReturn(true);
		when(mockEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

		String viewName = controller.addUser(user, false, true, mockModel);

		assertEquals("encodedPassword", user.getPassword());
		assertEquals(false, user.isAdmin());
		assertEquals(true, user.isInstructor());

		verify(mockRepo, times(1)).save(user);

		assertEquals("redirect:/addUser", viewName);
		assertEquals("User successfully added", AddUserController.addConfirmationMessage);
	}

}
