package gym_in_tech.webapp_14.proxies;

import gym_in_tech.webapp_14.pojos.Esercizio;
import gym_in_tech.webapp_14.pojos.Programma;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Programmi", url= "${programmi.service.url}")
public interface ProgrammiProxy {

    @GetMapping("/getProgrammi")
    public abstract List<Programma> getProgrammi();

    @PostMapping("/getEsercizi")
    public abstract List<Esercizio> getEsercizi(@RequestParam String nomeProg);

}
