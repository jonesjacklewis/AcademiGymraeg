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
import uk.ac.bangor.cs.cambria.AcademiGymraeg.util.UserService;

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
    
    @Autowired
    private UserService userService; //Get logged in user details
    
    private List<Question> questions;
    
    /**
     * Called when the Take Test button on the homepage is pressed.
     * 
     * @param model
     * @return String html template to render ("test")
     */
    @GetMapping({"/test"})
    public String takeTest(Model model) {
    	
    	Long id = userService.getLoggedInUserId();
    	
    	if(id == null) {
    		return "home";
    	}
    	
    	Optional<User> user = userRepo.findById(id);
    	
    	if(user.isEmpty()) {
    		return "home";
    	}
    	
    	User currentUser = user.get(); //Need to handle a missing user, but waiting until we decide on how to handle getting the current user.
    	
    	boolean isAdmin = userService.isLoggedInUserAdmin();
        boolean isInstructor = userService.isLoggedInUserInstructor();
    	
    	int numberOfQuestions = 20; //Did we plan on storing the default number of questions somewhere else?

        Test test = new Test(currentUser,  ZonedDateTime.now(), numberOfQuestions);

        testRepo.save(test);
        
        testConfig.generateQuestionsForTest(test);
        
        questions = questionRepo.findAllByTest(test);
        
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isInstructor", isInstructor);

        return "test"; // Return the name of the HTML template to render
    }

    
}

