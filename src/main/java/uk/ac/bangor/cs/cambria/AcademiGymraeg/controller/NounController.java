package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.enums.Gender;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Noun;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.NounRepository;

/**
 * @author ptg22svs
 */

@Controller
@RequestMapping("/noun")
public class NounController {
			
	@Autowired
	private NounRepository repo;
	
	private List<Gender> genders = Arrays.asList(Gender.values());
	
	@GetMapping
	public String nounAdminPage(Model m) {
		m.addAttribute("noun", new Noun());
		
		m.addAttribute("genders", genders);
		
		m.addAttribute("allnouns", repo.findAll());

		return "nounadmin";
	}

	
	  @PostMapping 
	  public String newnoun(Noun n, BindingResult result, Model m) {
		  repo.save(n);
		  
		  return nounAdminPage(m);
	  
	  }
	  
	  @GetMapping("/deletenoun/{id}")
	  @PreAuthorize("hasRole('ADMIN')")	  
	  public String deletenoun(@PathVariable("id") Long id)
	  {
		  repo.deleteById(id);
		  return "redirect:/noun";
	  }
	 
}
