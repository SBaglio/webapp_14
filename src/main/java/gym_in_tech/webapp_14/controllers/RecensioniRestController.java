package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.pojos.Recensione;
import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.RecensioneRepository;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecensioniRestController {

    private RecensioneRepository recensioneRepository;
    private UserRepository userRepository;

    @Autowired
    public RecensioniRestController(RecensioneRepository recensioneRepository, UserRepository userRepository){
        this.recensioneRepository=recensioneRepository;
        this.userRepository=userRepository;
    }

    @GetMapping("/getRecensioni")
    public List<Recensione> getRecensioni() {
        return recensioneRepository.findAll();
    }

    @PostMapping("/inviaRecensione")
    public List<Recensione> aggiungiRecensione(@RequestBody Recensione recensione, Authentication authentication) {
        User user = userRepository.getUser(authentication.getName());
        recensione.setNome(user.getFirstName());

        recensioneRepository.aggiungiRecensione(recensione);
        return recensioneRepository.findAll();
    }
}
