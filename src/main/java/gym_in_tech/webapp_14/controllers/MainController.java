package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.services.CheckUser;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    private final CheckUser checkUser;

    @Autowired
    MainController(CheckUser checkUser) {
        this.checkUser=checkUser;
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
    public String logout() {
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
    public String dashboardUserProva(Authentication authentication, Model model) {
        String name= authentication.getName();
        model.addAttribute("name", name);
        int  usufruiti= checkUser.userContaAllenamenti(name);
        model.addAttribute("allenamentiUsufruiti", usufruiti);
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
