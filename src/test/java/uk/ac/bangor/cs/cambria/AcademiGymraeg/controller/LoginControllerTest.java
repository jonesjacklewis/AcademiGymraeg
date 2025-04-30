package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author jcj23xfb
 */
public class LoginControllerTest {
	LoginController controller;

	@BeforeEach
	void setUp() {
		controller = new LoginController();
	}

	@Test
	void getHandler_default() {
		String viewName = controller.loginPage();

		assertEquals("login", viewName);
	}
}
