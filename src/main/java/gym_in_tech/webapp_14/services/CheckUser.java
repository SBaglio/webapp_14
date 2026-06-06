package gym_in_tech.webapp_14.services;

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


}
