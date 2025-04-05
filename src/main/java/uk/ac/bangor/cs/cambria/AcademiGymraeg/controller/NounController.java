package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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


/**
 * All requests to "/noun" (and subdocs) are handled by this controller
 */

@Controller
@RequestMapping("/noun")
public class NounController {
	
	//TODO - Add error handling
	//TODO - Add confirmation messages for additions, changes or deletions
	
	@Autowired
	private NounRepository repo;
	
	private List<Gender> genders = Arrays.asList(Gender.values());
	
	
	//GET requests to "/noun" (Initial request) - new Noun object is passed in, 
	//in preparation to adding a new noun to the data store.
	//All GENDER enum values are also passed in, to display as radio button options on the New Noun form.
	//All existing noun objects are also passed in as a List, to display in the All Nouns table.
	@GetMapping
	public String nounAdminPage(Model m) {
		m.addAttribute("noun", new Noun());
		
		m.addAttribute("genders", genders);
		
		m.addAttribute("allnouns", repo.findAll());

		return "nounadmin";
	}

	
	//POST requests to "/noun". Handles validating entry into the New Noun form, before creating a 
	//new Noun entry in the data store
	  @PostMapping 
	  public String newNoun(Noun n, BindingResult result, Model m) throws Exception {
		  
		//Validate what has been entered - alphabet only, no spaces
		  	  
		  String regex = "([a-zA-Z])\\w*";
		  
		  if(!n.getEnglishNoun().matches(regex))
		  {
			  throw new Exception("Invalid input for English noun. Only letters allowed, no spaces");
		  }
		  else if(!n.getWelshNoun().matches(regex))
		  {
			  throw new Exception("Invalid input for Welsh noun. Only letters allowed, no spaces");
		  }
		  
		  //Convert to lowercase
		  n.setEnglishNoun(n.getEnglishNoun().toLowerCase());
		  n.setWelshNoun(n.getWelshNoun().toLowerCase());
		  
		  repo.save(n);
		  
		  return nounAdminPage(m);
	  
	  }
	  
	  //GET requests to "/noun/deletenoun/{nounID}. Handles deleting a requested Noun from the data store.
	  @GetMapping("/deletenoun/{id}")
	  public String deletenoun(@PathVariable("id") Long id)
	  {
		  repo.deleteById(id);
		  return "redirect:/noun";
	  }
	  
	  //POST requests to "/noun/editnoun". Handles editing an existing Noun in the data store
	  @PostMapping("/editnoun")
	  public String editnoun(Noun noun, BindingResult result, Model m) throws Exception {
		  
		  
		  
		//Validate what has been entered - alphabet only, no spaces
	  	  
		  String regex = "([a-zA-Z])\\w*";
		  
		  if(!noun.getEnglishNoun().matches(regex))
		  {
			  throw new Exception("Invalid input for English noun. Only letters allowed, no spaces");
		  }
		  else if(!noun.getWelshNoun().matches(regex))
		  {
			  throw new Exception("Invalid input for Welsh noun. Only letters allowed, no spaces");
		  }
		  
		  //Convert to lowercase
		  noun.setEnglishNoun(noun.getEnglishNoun().toLowerCase());
		  noun.setWelshNoun(noun.getWelshNoun().toLowerCase());
		  
		  
		  repo.save(noun);
		  
		  return "redirect:/noun";
	  
	  }
	  
	  //GET requests to "/noun/editnoun/{nounID}. Handles initial request to edit a noun by presenting
	  //the "editnoun" page to the user, prepopulated with the existing Noun details.
	  @GetMapping("/editnoun/{id}")
		public String nounEditPage(@PathVariable("id") Long id, Model m) {
			m.addAttribute("noun", repo.findById(id).get());
			
			m.addAttribute("genders", genders);
			
			return "nounedit";
		}
	 
}
