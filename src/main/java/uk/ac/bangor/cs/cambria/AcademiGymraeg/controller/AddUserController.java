package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author grs22lkc
 */

@Controller
public class AddUserController {
	@GetMapping("/addUser")
	public String addUserPage() {
		return "useradmin";
	}
}
