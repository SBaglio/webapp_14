package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminDashboardController {
    private UserRepository userRepository;

    @Autowired
    public AdminDashboardController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @GetMapping("/listaUtenti")
    public String listaUtenti(Model model) {
        List<User> utenti = userRepository.findAllUsers();
        model.addAttribute("utenti", utenti);
        return "listaUtenti";
    }

    @PostMapping("/disabilita-scaduti")
    @ResponseBody
    public ResponseEntity<Integer> rimuoviUtentiScaduti() {
        try {
            int utentiRimossi = userRepository.deleteDisabledUserProva();

            return ResponseEntity.ok(utentiRimossi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }
}
