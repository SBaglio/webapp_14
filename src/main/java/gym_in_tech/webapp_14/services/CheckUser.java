package gym_in_tech.webapp_14.services;

import gym_in_tech.webapp_14.pojos.User;
import gym_in_tech.webapp_14.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckUser {

    @Autowired
    private UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    public void addUser(String firstName, String lastName, String birthDate,
                        String email, String username, String password, String piano) {
        String role = switch (piano) {
            case "Basic" -> "ROLE_USER_BASIC";
            case "Pro"   -> "ROLE_USER_PRO";
            default      -> "ROLE_USER_PROVA";
        };

        User user = new User(firstName, lastName, birthDate, email, username, password, role);
        userRepository.addUser(user);
    }


}