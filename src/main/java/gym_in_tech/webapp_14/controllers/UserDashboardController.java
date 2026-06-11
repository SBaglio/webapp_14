package gym_in_tech.webapp_14.controllers;


import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/programmi")
    public String visualizzaProgrammi(Authentication authentication, Model model){
        return "programmi";
    }

    @PostMapping("/upgradePlan")
    public String upgradePlan(@RequestParam String piano, Authentication authentication){
        String role = switch (piano) {
            case "Basic" -> "ROLE_USER_BASIC";
            case "Pro"   -> "ROLE_USER_PRO";
            default      -> "ROLE_USER_PROVA";
        };

        User user = userRepository.getUser(authentication.getName());
        userRepository.upgradePlan(user, role);

        // Dobbiamo costruire un nuovo oggetto Authentication e riaggiornare il SecurityContext per reindirizzare l'utente alla nuova dashboard
        List<GrantedAuthority> nuoveAuthorities = List.of(new SimpleGrantedAuthority(role));
        Authentication nuovaAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), nuoveAuthorities);
        SecurityContextHolder.getContext().setAuthentication(nuovaAuth);

        return "redirect:/dashboard";
    }

    @GetMapping("/modificaPassword")
    public String modificaPassword(){
        return "cambioPassword";
    }

    @PostMapping("/cambiaPassword")
    public String cambiaPassword(@RequestParam String vecchiaPassword,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword,
                                 Authentication authentication,
                                 Model model) {

        User user = userRepository.getUser(authentication.getName());

        // Ridondante ma sicuro: controlla che le due nuove password coincidano
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Le nuove password non coincidono.");
            return "cambioPassword";
        }

        if (!userRepository.checkPassword(user, vecchiaPassword)){
            model.addAttribute("errorMessage", "La password attuale inserita non è corretta.");
            return "cambioPassword";
        } else {
            userRepository.cambiaPassword(user, password);
        }

        return "redirect:/dashboard?passwordChanged=true";
    }

    @GetMapping("/statisticheUser")
    public String visualizzaStatisticheUser(Authentication authentication, Model model){
        model.addAttribute("name", authentication.getName());
        return "statisticheUser";
    }

}
