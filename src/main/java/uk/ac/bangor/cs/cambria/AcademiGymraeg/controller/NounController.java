package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;

/**
 * @author ptg22svs
 */

@Controller
public class NounController {
	
	@GetMapping("/noun")	
	public String nounAdminPage(Model m) {
		m.addAttribute("noun", new Noun());
		
		return "nounadmin";
	}
	
	
	@PostMapping
	public String newNoun(BindingResult result, Model m) {

		
	}
}

