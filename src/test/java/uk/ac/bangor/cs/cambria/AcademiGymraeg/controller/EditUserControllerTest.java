package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.EditUserDTO;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.ValidatorService;

/**
 * @author jcj23xfb
 */
public class EditUserControllerTest {

	private EditUserController controller;

	private UserRepository mockUserRepo;
	private TestRepository mockTestRepo;
	private ValidatorService mockValidator;
	private Model mockModel;
	private BindingResult mockBindingResult;

	@BeforeEach
	void setUp() {
		mockUserRepo = mock(UserRepository.class);
		mockTestRepo = mock(TestRepository.class);
		mockValidator = mock(ValidatorService.class);
		mockBindingResult = mock(BindingResult.class);
		mockModel = mock(Model.class);

		controller = new EditUserController();

		controller.userRepo = mockUserRepo;
		controller.testRepo = mockTestRepo;
		controller.validator = mockValidator;
	}

	@Test
	void getHandler_whenNoUserForId() {
		when(mockUserRepo.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.userEditPage(1L, mockModel);

		assertEquals("redirect:/viewusers", viewName);

		verify(mockUserRepo, times(1)).findById(1L);
	}

	@Test
	void getHandler_whenUserNotInModel() {
		User user = new User();

		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.userEditPage(1L, mockModel);
		assertEquals("useredit", viewName);

		verify(mockModel).addAttribute(eq("editUserDTO"), any(EditUserDTO.class));
	}

	@Test
	void getHandler_whenUserInModel() {
		User user = new User();
		EditUserDTO dto = new EditUserDTO();

		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));
		when(mockModel.containsAttribute("editUserDTO")).thenReturn(true);

		String viewName = controller.userEditPage(1L, mockModel);
		assertEquals("useredit", viewName);

		verify(mockModel, never()).addAttribute("editUserDTO", dto);
	}

	@Test
	void postHandler_resultHasErrors() {
		User user = new User();
		user.setUserId(1L);

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);

		when(mockBindingResult.hasErrors()).thenReturn(true);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		verify(mockModel).addAttribute("user", dto);
		assertEquals("useredit", viewName);
	}

	@Test
	void postHandler_invalidEmail() {
		User user = new User();
		user.setUserId(1L);

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);
		dto.setUsername("adminEmail");

		when(mockValidator.isValidEmail(dto.getUsername(), true)).thenReturn(false);

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		verify(mockValidator).isValidEmail(dto.getUsername(), true);
		verify(mockBindingResult).rejectValue(eq("username"), eq("error.username"), anyString());
		verify(mockModel).addAttribute("user", dto);
		assertEquals("useredit", viewName);
	}

	@Test
	void postHandler_noneMatchingPasswords() {
		User user = new User();
		user.setUserId(1L);

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);
		dto.setUsername("adminEmail@mail.com");
		dto.setNewPassword("Password");
		dto.setConfirmPassword("Password123!");

		when(mockValidator.isValidEmail(dto.getUsername(), true)).thenReturn(true);

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		verify(mockValidator).isValidEmail(dto.getUsername(), true);
		verify(mockBindingResult).rejectValue(eq("password"), eq("error.newPassword"),
				eq("The passwords do not match."));
		verify(mockModel).addAttribute("user", dto);
		assertEquals("useredit", viewName);
	}

	@Test
	void postHandler_invalidPasswords() {
		User user = new User();
		user.setUserId(1L);

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);
		dto.setUsername("adminEmail@mail.com");
		dto.setNewPassword("Password");
		dto.setConfirmPassword("Password");

		when(mockValidator.isValidEmail(dto.getUsername(), true)).thenReturn(true);
		when(mockValidator.isValidPassword(dto.getNewPassword())).thenReturn(false);

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		verify(mockValidator).isValidEmail(dto.getUsername(), true);
		verify(mockBindingResult).rejectValue(eq("password"), eq("error.newPassword"), eq(
				"Password does not meet complexity requirements. Must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character."));
		verify(mockModel).addAttribute("user", dto);
		assertEquals("useredit", viewName);
	}

	@Test
	void postHandler_userIdNotExist() {
		User user = new User();
		user.setUserId(1L);

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);
		dto.setUsername("adminEmail@mail.com");
		dto.setNewPassword("Password");
		dto.setConfirmPassword("Password");

		when(mockValidator.isValidEmail(dto.getUsername(), true)).thenReturn(true);
		when(mockValidator.isValidPassword(dto.getNewPassword())).thenReturn(true);

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		assertEquals("redirect:/home", viewName);
	}

	@Test
	void postHandler_successfulEdit() {
		User user = new User();
		user.setUserId(1L);
		user.setUsername("old@mail.com");
		user.setPassword("OldPassword123!");

		EditUserDTO dto = new EditUserDTO();
		dto.setUserId(1L);
		dto.setUsername("adminEmail@mail.com");
		dto.setNewPassword("NewPassword123!");
		dto.setConfirmPassword("NewPassword123!");
		dto.setForename("Updated");
		dto.setAdmin(true);
		dto.setInstructor(false);

		when(mockValidator.isValidEmail(dto.getUsername(), true)).thenReturn(true);
		when(mockValidator.isValidPassword(dto.getNewPassword())).thenReturn(true);
		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(user));

		String viewName = controller.editUser(dto, mockBindingResult, mockModel);

		assertEquals("redirect:/viewusers", viewName);

		assertEquals("adminEmail@mail.com", user.getUsername());
		assertEquals("NewPassword123!", user.getPassword());
		assertEquals("Updated", user.getForename());
		assertTrue(user.isAdmin());
		assertFalse(user.isInstructor());

		verify(mockUserRepo).save(user);
	}

	@Test
	void getHandlerDelete_userIdNull() {
		String viewName = controller.deleteUser(null);

		assertEquals("redirect:/viewusers", viewName);
	}

	@Test
	void getHandlerDelete_userNotExists() {
		when(mockUserRepo.findById(1L)).thenReturn(Optional.empty());

		String viewName = controller.deleteUser(1L);

		assertEquals("redirect:/viewusers", viewName);
	}

	@Test
	void getHandlerDelete_success() {

		User u = new User();
		u.setUserId(1L);

		when(mockUserRepo.findById(1L)).thenReturn(Optional.of(u));

		String viewName = controller.deleteUser(1L);

		verify(mockUserRepo).delete(u);
		verify(mockTestRepo).deleteAllByUser(u);

		assertEquals("redirect:/viewusers", viewName);
	}

}
