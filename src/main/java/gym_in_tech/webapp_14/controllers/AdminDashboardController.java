package gym_in_tech.webapp_14.controllers;

import gym_in_tech.webapp_14.pojos.StatisticaProgrammi;
import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.proxies.ProgrammiProxy;
import gym_in_tech.webapp_14.repositories.StatisticaProgrammiRepository;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminDashboardController {
    private UserRepository userRepository;
    private StatisticaProgrammiRepository statisticaProgrammiRepository;
    private ProgrammiProxy programmiProxy;

    @Autowired
    public AdminDashboardController(UserRepository userRepository, StatisticaProgrammiRepository statisticaProgrammiRepository, ProgrammiProxy programmiProxy){
        this.userRepository=userRepository;
        this.statisticaProgrammiRepository=statisticaProgrammiRepository;
        this.programmiProxy=programmiProxy;
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

    @GetMapping("/statisticheAdmin")
    public String getStatisticheAdmin(Model model){
        List<StatisticaProgrammi> statistiche = statisticaProgrammiRepository.getMedieEsecuzioniPerRuolo(programmiProxy.getProgrammi());

        List<StatisticaProgrammi> statisticheBasic = new ArrayList<StatisticaProgrammi>();
        List<StatisticaProgrammi> statistichePro = new ArrayList<StatisticaProgrammi>();

        for (StatisticaProgrammi stat: statistiche){
            if (stat.getRuolo().equals("ROLE_USER_BASIC")){
                statisticheBasic.add(stat);
            } else if (stat.getRuolo().equals("ROLE_USER_PRO")){
                statistichePro.add(stat);
            }
        }

        model.addAttribute("statisticheBasic", statisticheBasic);
        model.addAttribute("statistichePro", statistichePro);

        return "statisticheAdmin";
    }
}
