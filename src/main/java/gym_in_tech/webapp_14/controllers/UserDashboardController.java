package gym_in_tech.webapp_14.controllers;


import gym_in_tech.webapp_14.pojos.Esercizio;
import gym_in_tech.webapp_14.pojos.Programma;
import gym_in_tech.webapp_14.pojos.Recensione;
import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.proxies.KCalCalculatorProxy;
import gym_in_tech.webapp_14.proxies.ProgrammiProxy;
import gym_in_tech.webapp_14.repositories.ProgrammaRepository;
import gym_in_tech.webapp_14.repositories.RecensioneRepository;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserDashboardController {

    private final UserRepository userRepository;
    private final ProgrammiProxy programmi;
    private final ProgrammaRepository programmaRepository;
    private final KCalCalculatorProxy kCalCalculatorProxy;
    private final RecensioneRepository recensioneRepository;

    @Autowired
    public UserDashboardController (UserRepository userRepository, ProgrammiProxy programmi, ProgrammaRepository programmaRepository, KCalCalculatorProxy kCalCalculatorProxy, RecensioneRepository recensioneRepository){
        this.userRepository=userRepository;
        this.programmi=programmi;
        this.programmaRepository = programmaRepository;
        this.kCalCalculatorProxy = kCalCalculatorProxy;
        this.recensioneRepository=recensioneRepository;
    }

    @GetMapping("/profilo")
    public String visualizzaProfilo(Authentication authentication, Model model){
        User user = userRepository.getUser(authentication.getName());
        model.addAttribute("nome", user.getFirstName());
        model.addAttribute("cognome", user.getLastName());
        model.addAttribute("dataNascita", user.getBirthDate());
        model.addAttribute("ruolo", userRepository.getRole(user.getUsername()));

        return "profilo";
    }

    @GetMapping("/programmi")
    public String visualizzaProgrammi(){
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

    @GetMapping("/allenamenti")
    public String visualizzaAllenamenti(Model model){
        model.addAttribute("programmiPredefiniti", programmi.getProgrammi() );

        return "allenamenti";
    }

    @GetMapping ("/allenamentiPro")
    public String visualizzaAllenamentiPro(Authentication authentication, Model model){
        model.addAttribute("programmiPredefiniti", programmi.getProgrammi() );
        model.addAttribute("programmiPersonalizzati", programmaRepository.findUserPrograms(authentication.getName()));

        return "allenamentiPro";
    }

    @GetMapping("/completato")
    public String completato(Authentication authentication){
        userRepository.addAllenamento(authentication.getName());

        return "redirect:/dashboard";
    }


    @PostMapping("/creaProgramma")
    public String creaProgramma(@ModelAttribute Programma programma, Authentication authentication, Model model){

        float kcal=kCalCalculatorProxy.countKCal(programma);
        programma.setKcal(kcal);

        long idProg = programmaRepository.aggiungiProgramma(programma, authentication.getName());
        for (Esercizio es : programma.getEsercizi()) {
            programmaRepository.aggiungiEsercizio(idProg, es);
        }

        model.addAttribute("messaggioSuccesso", "Programma salvato con successo!");
        model.addAttribute("kcalCalcolate", kcal);

        return "/programmi";
    }

//    @PostMapping("/recensioni")
//    @ResponseBody
//    public Recensione addRecensione(@RequestParam ) {
//        recensioneRepository.aggiungiRecensione(r);
//        return r;
//    }

}
