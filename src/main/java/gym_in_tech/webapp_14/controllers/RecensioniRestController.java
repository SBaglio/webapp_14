package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.pojos.Recensione;
import gym_in_tech.webapp_14.repositories.RecensioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecensioniRestController {
    @Autowired
    private RecensioneRepository recensioneRepository;

    @GetMapping("/getRecensioni")
    public List<Recensione> getRecensioni() {
        return recensioneRepository.findAll();
    }

    @PostMapping("/inviaRecensione")
    public List<Recensione> aggiungiRecensione(@RequestBody Recensione recensione, Authentication authentication) {
        String username = authentication.getName();
        recensione.setUsername(username);

        recensioneRepository.aggiungiRecensione(recensione);
        return recensioneRepository.findAll();
    }
}
