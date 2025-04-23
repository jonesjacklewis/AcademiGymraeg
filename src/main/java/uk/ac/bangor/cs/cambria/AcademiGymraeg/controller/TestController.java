package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;

import java.time.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.TestConfigurer;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Question;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.Test;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.QuestionRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.TestRepository;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

/**
 * @author dwp22pzv
 */

@Controller
public class TestController {

    @Autowired
    private TestRepository testRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private TestConfigurer testConfig;
    
    private List<Question> questions;
    
    //@GetMapping({"/{id}", "/test"})
    //public String takeTest(@PathVariable("id") Long id) {
    @GetMapping({"/test"})
    public String takeTest(Model model) {
    	
    	Long id = (long) 1; //TODO: Replace with an actual user ID, this one is just a placeholder
    	
    	System.out.println("Id: " + id);
    	
    	Optional<User> user = userRepo.findById(id);
    	
    	User currentUser = user.get(); //Need to handle a missing user, but waiting until we decide on how to handle getting the current user.
    	
    	System.out.println("User: " + user);
    	
    	ZonedDateTime timeOfTest = ZonedDateTime.now();
    	
    	System.out.println("Time: " + timeOfTest);
    	
    	int numberOfQuestions = 20; //Did we plan on storing the default number of questions somewhere else?

        Test test = new Test(currentUser,  timeOfTest, numberOfQuestions);

        testRepo.save(test);
        
        testConfig.generateQuestionsForTest(test);
        
        questions = questionRepo.findAllByTest(test);
        
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);


        return "test"; // Return the name of the HTML template to render
    }

    
}

