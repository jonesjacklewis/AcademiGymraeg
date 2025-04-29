package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.NounService;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

/**
 * @author ptg22svs, jcj23xfb
 */
public class NounControllerTest {

	NounController controller;

	private UserService mockUserService;
	private NounService mockNounService;
	private Model mockModel;

	private BindingResult mockBindingResult;

	@BeforeEach
	void setUp() {
		mockUserService = mock(UserService.class);
		mockNounService = mock(NounService.class);
		mockModel = mock(Model.class);
		mockBindingResult = mock(BindingResult.class);

		controller = new NounController();

		controller.userService = mockUserService;
		controller.nounService = mockNounService;
	}

	@Test
	void setMessage_nullMessage() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			NounController.setMessage(null, "add");
		});
		assertEquals("Null argument passed", exception.getMessage());
	}

	@Test
	void setMessage_nullType() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			NounController.setMessage("message", null);
		});
		assertEquals("Null argument passed", exception.getMessage());
	}

	@Test
	void setMessage_add() {
		NounController.setMessage("message", "add");

		assertEquals("message", NounController.addConfirmationMessage);
	}

	@Test
	void setMessage_edit() {
		NounController.setMessage("message", "edit");

		assertEquals("message", NounController.editConfirmationMessage);
	}

	@Test
	void setMessage_delete() {
		NounController.setMessage("message", "delete");

		assertEquals("message", NounController.deleteConfirmationMessage);
	}

	@Test
	void clearMessages_test() {
		NounController.addConfirmationMessage = "a";
		NounController.editConfirmationMessage = "b";
		NounController.deleteConfirmationMessage = "c";

		NounController.clearMessages();

		assertEquals("", NounController.addConfirmationMessage);
		assertEquals("", NounController.editConfirmationMessage);
		assertEquals("", NounController.deleteConfirmationMessage);
	}

	@Test
	void getHandlerNounAdmin_userIdNull() {
		when(mockUserService.getLoggedInUserId()).thenReturn(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.nounAdminPage(mockModel);
		});

		assertEquals("Unable to get logged in user", exception.getMessage());
	}

	@Test
	void getHandlerNounAdmin_addNounAtt() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);

		String viewName = controller.nounAdminPage(mockModel);

		verify(mockModel).addAttribute(eq("noun"), any(Noun.class));
		assertEquals(viewName, "nounadmin");
	}

	@Test
	void getHandlerNounAdmin_noNounAtt() {
		when(mockUserService.getLoggedInUserId()).thenReturn(1L);
		when(mockModel.containsAttribute("noun")).thenReturn(true);

		String viewName = controller.nounAdminPage(mockModel);

		verify(mockModel, never()).addAttribute(eq("noun"), any(Noun.class));
		assertEquals(viewName, "nounadmin");
	}

	@Test
	void postHandlerNewNoun_nullNoun() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.newNoun(null, mockBindingResult, mockModel);
		});

		assertEquals("Null Noun argument", exception.getMessage());
	}

	@Test
	void postHandlerNewNoun_resultHasErrors() {
		Noun noun = new Noun();

		when(mockBindingResult.hasErrors()).thenReturn(true);

		String viewName = controller.newNoun(noun, mockBindingResult, mockModel);

		verify(mockModel).addAttribute("noun", noun);
		assertEquals("nounadmin", viewName);
	}

	@Test
	void postHandlerNewNoun_successAdd() {
		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		String message = "New noun '" + noun.getWelshNoun() + " | " + noun.getEnglishNoun() + "' added.";

		when(mockBindingResult.hasErrors()).thenReturn(false);

		String viewName = controller.newNoun(noun, mockBindingResult, mockModel);

		verify(mockNounService).saveRecord(any(Noun.class));

		assertEquals("redirect:/noun", viewName);
		assertEquals(NounController.addConfirmationMessage.toLowerCase(), message.toLowerCase());
	}

	@Test
	void postHandlerDeleteNoun_nullNounId() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.deleteNoun(null);
		});

		assertEquals("Null id argument", exception.getMessage());
	}

	@Test
	void postHandlerDeleteNoun_unknownNounId() {

		when(mockNounService.getById(1L)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.deleteNoun(1L);
		});

		assertEquals("Unknown Noun Id", exception.getMessage());
	}

	@Test
	void postHandlerDeleteNoun_success() {

		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		noun.setNounId(1L);

		String message = "Noun '" + noun.getWelshNoun() + " | " + noun.getEnglishNoun() + "' was deleted.";

		when(mockNounService.getById(1L)).thenReturn(Optional.of(noun));

		String viewName = controller.deleteNoun(1L);

		assertEquals(viewName, "redirect:/noun");
		assertEquals(NounController.deleteConfirmationMessage.toLowerCase(), message.toLowerCase());
		verify(mockNounService).deleteById(1L);
	}

	@Test
	void postHandlerEditNoun_nullNoun() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.editNoun(null, mockBindingResult, mockModel);
		});

		assertEquals("Null Noun argument", exception.getMessage());
	}

	@Test
	void postHandlerEditNoun_resultHasErrors() {
		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		noun.setNounId(1L);

		when(mockBindingResult.hasErrors()).thenReturn(true);
		when(mockNounService.getById(1L)).thenReturn(Optional.of(noun));

		String viewName = controller.editNoun(noun, mockBindingResult, mockModel);

		verify(mockModel, atLeastOnce()).addAttribute("noun", noun);
		assertEquals("nounedit", viewName);
	}

	@Test
	void postHandlerEditNoun_success() {
		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		noun.setNounId(1L);

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockNounService.getById(1L)).thenReturn(Optional.of(noun));

		String viewName = controller.editNoun(noun, mockBindingResult, mockModel);

		verify(mockModel, atLeastOnce()).addAttribute(eq("noun"), any(Noun.class));
		verify(mockNounService).saveRecord(any(Noun.class));

		assertEquals("redirect:/noun", viewName);
		assertEquals("changes to noun were saved.", NounController.editConfirmationMessage.toLowerCase());
	}

	@Test
	void getHandlerEditNoun_nullNounId() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.nounEditPage(null, mockModel);
		});

		assertEquals("Null id argument", exception.getMessage());
	}

	@Test
	void getHandlerEditNoun_emptyNoun() {

		when(mockNounService.getById(1L)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.nounEditPage(1L, mockModel);
		});

		assertEquals("Unknown Noun Id", exception.getMessage());
	}

	@Test
	void getHandlerEditNoun_noNounAttribute() {

		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		noun.setNounId(1L);

		when(mockNounService.getById(1L)).thenReturn(Optional.of(noun));
		when(mockModel.containsAttribute("noun")).thenReturn(false);

		String viewName = controller.nounEditPage(1L, mockModel);

		verify(mockModel, atLeastOnce()).addAttribute(eq("noun"), any(Noun.class));
		verify(mockModel).addAttribute(eq("genders"), ArgumentMatchers.<List<Gender>>any());

		assertEquals(viewName, "nounedit");
	}

	@Test
	void getHandlerEditNoun_hasNounAttribute() {

		Noun noun = new Noun();
		noun.setEnglishNoun("Dog");
		noun.setWelshNoun("Ci");
		noun.setGender(Gender.MASCULINE);
		noun.setNounId(1L);

		when(mockNounService.getById(1L)).thenReturn(Optional.of(noun));
		when(mockModel.containsAttribute("noun")).thenReturn(true);

		String viewName = controller.nounEditPage(1L, mockModel);

		verify(mockModel, never()).addAttribute(eq("noun"), any(Noun.class));
		verify(mockModel).addAttribute(eq("genders"), ArgumentMatchers.<List<Gender>>any());

		assertEquals(viewName, "nounedit");
	}

}
