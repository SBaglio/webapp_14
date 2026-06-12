package gym_in_tech.webapp_14.proxies;

import gym_in_tech.webapp_14.configurations.OpenFeignConfig;
import gym_in_tech.webapp_14.pojos.Programma;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kcal", url= "${programmi.service.url}")
public interface KCalCalculatorProxy {

    @PostMapping("/countKcal")
    public abstract float countKCal(@RequestBody Programma programma);
}
