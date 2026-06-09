package gym_in_tech.webapp_14.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@ComponentScan("gym_in_tech.webapp_14")
public class SecurityConfig {

    // User details manager
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // Password encoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security chain
    @Bean
    public SecurityFilterChain configure(HttpSecurity http)
            throws Exception {

        // Authentication
        http.formLogin(c ->
                c.loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
        );

        // Authorization
        http.authorizeHttpRequests(c ->
                c.requestMatchers("/dashboard").hasAnyRole("ADMIN", "USER_PROVA", "USER_BASIC", "USER_PRO")
                        .requestMatchers("/DashboardUserProva").hasRole("USER_PROVA")
                        .requestMatchers("/DashboardUserBasic").hasRole("USER_BASIC")
                        .requestMatchers("/DashboardUserPro").hasRole("USER_PRO")
                        .requestMatchers("/DashboardAdmin").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );

        // Logout
        http.logout(c ->
                c.logoutUrl("/perform_logout") // It is the Spring Security logout endpoint
                        .logoutSuccessUrl("/logout") //Pay attention it is using a GET-redirect under the hood! So, in the Controller you should use @GetMapping

        );

        // TO DISABLE CSRF PROTECTION
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
