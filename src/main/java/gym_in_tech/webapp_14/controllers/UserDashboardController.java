package gym_in_tech.webapp_14.controllers;


import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDashboardController {

    private final UserRepository userRepository;

    @Autowired
    public UserDashboardController (UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @GetMapping("/profilo")
    public String visualizzaProfilo(Authentication authentication, Model model){
        User user = userRepository.getUser(authentication.getName());
        model.addAttribute("nome", user.getFirstName() );
        model.addAttribute("cognome", user.getLastName());
        //model.addAttribute("dataNascita", user.getDateOfBirth());
        model.addAttribute("ruolo", user.getRole());

        return "profilo";
    }



}
