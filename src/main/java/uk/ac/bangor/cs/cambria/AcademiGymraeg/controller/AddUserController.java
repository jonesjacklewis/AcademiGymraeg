package uk.ac.bangor.cs.cambria.AcademiGymraeg.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.bangor.cs.cambria.AcademiGymraeg.model.User;
import uk.ac.bangor.cs.cambria.AcademiGymraeg.repo.UserRepository;

@Controller
public class AddUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/useradmin")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "useradmin";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, 
                          @RequestParam(value = "admin", required = false, defaultValue = "false") boolean admin,
                          @RequestParam(value = "instructor", required = false, defaultValue = "false") boolean instructor,
                          Model model) {
        user.setAdmin(admin);
        user.setInstructor(instructor);
        userRepository.save(user);
        model.addAttribute("message", "User added successfully!");
        return "redirect:/useradmin";
    }
}