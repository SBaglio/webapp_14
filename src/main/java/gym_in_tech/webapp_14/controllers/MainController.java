package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.repositories.UserRepository;
import gym_in_tech.webapp_14.services.CheckUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final CheckUser checkUser;
    private final UserRepository userRepository;

    @Autowired
    MainController(CheckUser checkUser, UserRepository userRepository) {
        this.checkUser=checkUser;
        this.userRepository=userRepository;
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


    // Signing up
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String birthDate,
                          @RequestParam String email,
                          @RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String piano,
                          Model model) {

        String returnPage;
        if (checkUser.userExists(username)) {
            model.addAttribute("errorMessage", "Username '" + username + "' già in uso. Scegline un altro.");
            returnPage = "signup";
        } else {
            checkUser.addUser(firstName, lastName, birthDate, email, username, password, piano);
            model.addAttribute("username", username);
            returnPage = "registrazioneConSuccesso";
        }
        return returnPage;
    }


    // Logging out
    @GetMapping("/logout")
    public String logout(@RequestParam(value = "trialEnded", required = false) Boolean trialEnded, Model model) {
        model.addAttribute("trialEnded", trialEnded != null && trialEnded);

        return "logout";
    }

    //Dashboards

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        String returnPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            returnPage = "forward:DashboardAdmin";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER_PROVA"))){
            returnPage = "forward:DashboardUserProva";
        } else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER_BASIC"))){
            returnPage = "forward:DashboardUserBasic";
        } else {
            returnPage = "forward:DashboardUserPro";
        }
        return returnPage;
    }

    @GetMapping("/DashboardUserProva")
    public String dashboardUserProva(Authentication authentication, Model model, HttpServletRequest request, HttpServletResponse response) {
        String name= authentication.getName();
        int  usufruiti= userRepository.userContaAllenamenti(name);
        if (usufruiti>=3){
            userRepository.disableUser(name);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/logout?trialEnded=true";
        }
        model.addAttribute("allenamentiUsufruiti", usufruiti);
        model.addAttribute("name", name);
        return "DashboardUserProva";
    }

    @GetMapping("/DashboardUserBasic")
    public String dashBoardUserBasic(Authentication authentication, Model model){
        String name= authentication.getName();
        model.addAttribute("name", name);
        return "DashboardUserBasic";
    }

    @GetMapping("/DashboardUserPro")
    public String dashBoardUserPro(Authentication authentication, Model model){
        String name= authentication.getName();
        model.addAttribute("name", name);
        return "DashboardUserPro";
    }

    @GetMapping("/DashboardAdmin")
    public String dashboardAdmin(Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        return "DashboardAdmin";
    }



}
