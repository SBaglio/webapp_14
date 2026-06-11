package gym_in_tech.webapp_14.configurations;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("gym_in_tech.webapp_14")
@EnableFeignClients(basePackages = "gym_in_tech.webapp_14")
public class OpenFeignConfig {

}
