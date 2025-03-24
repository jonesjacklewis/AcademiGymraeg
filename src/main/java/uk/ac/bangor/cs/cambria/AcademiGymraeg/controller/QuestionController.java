package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;

/**
 * @author dwp22pzv
 */

@Controller
public class QuestionController {

	@Autowired
	private QuestionRepository repo;
	
	//https://stackoverflow.com/questions/24279186/fetch-random-records-using-spring-data-jpa
	
	//private List<Noun> = repo.

}
