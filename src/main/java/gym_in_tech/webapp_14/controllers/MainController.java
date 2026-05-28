package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final UserRepository userRepository;

    @Autowired
    MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/contattaci")
    public String contattaci(){return "contatti";}

    // Logging in
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }


    // Signing up
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String username,
                          @RequestParam String password,
                          Model model) {
        String returnPage;
        if (userRepository.userExists(username)) {
            System.out.println("User already exists");
            model.addAttribute("username", username);
            returnPage = "signupFailure";
        } else {
            userRepository.addUser(new User(firstName, lastName, email, username, password, "ROLE_USER"));
            model.addAttribute("username", username);
            returnPage = "registrazioneConSuccesso";
        }
        return returnPage;
    }

    // Logging out
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

//    // Dashboards
//    @GetMapping("/userDashboard")
//    public String userDashboard(Authentication authentication, Model model) {
//        model.addAttribute("name", authentication.getName());
//        return "userDashboard";
//    }
//
//    @GetMapping("/adminDashboard")
//    public String adminDashboard(Authentication authentication, Model model) {
//        model.addAttribute("name", authentication.getName());
//        return "adminDashboard";
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboard(Authentication authentication) {
//        String returnPage;
//        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//            returnPage = "forward:adminDashboard";
//        } else {
//            returnPage = "forward:userDashboard";
//        }
//        return returnPage;
//    }


}
