package gym_in_tech.webapp_14.repositories;

import gym_in_tech.webapp_14.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository(JdbcTemplate jdbc, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.jdbc = jdbc;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }


    public List<User> findAllUsers() {
        String sql = "SELECT * FROM USERDATA";
        RowMapper<User> userRowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getInt("ID"));
            rowObject.setUsername(r.getString("USERNAME"));
            rowObject.setFirstName(r.getString("FIRSTNAME"));
            rowObject.setLastName(r.getString("LASTNAME"));
            rowObject.setEmail(r.getString("EMAIL"));
            return rowObject;
        };
        return jdbc.query(sql, userRowMapper);
    }

    @Transactional
    public void addUser(User user) {
        String sql = "INSERT INTO USERDATA VALUES (DEFAULT, ?, ?, ?,?, ?)";
        jdbc.update(sql,
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //userDetailsManager.createUser(new SecurityUser(user));
    }

    public User getUser(String username){
        String sql = "SELECT * FROM USERDATA where username= ?";
        RowMapper<User> userRowMapper = (r, i) -> {
            User rowObject = new User();
            rowObject.setId(r.getInt("ID"));
            rowObject.setUsername(r.getString("USERNAME"));
            rowObject.setFirstName(r.getString("FIRSTNAME"));
            rowObject.setLastName(r.getString("LASTNAME"));
            rowObject.setEmail(r.getString("EMAIL"));
            return rowObject;
        };

        return jdbc.queryForObject(sql, userRowMapper, username);
    }

    public int userContaAllenamenti(String username){
        String sql = "SELECT numAllenamenti FROM USERDATA WHERE username = ?";

        // queryForObject esegue la query e si aspetta come risultato un Integer
        return jdbc.queryForObject(sql, Integer.class, username);

    }

    public void upgradePlan(User user, String role){
        user.setRole(role);
        String sql = "UPDATE authorities SET authority = ? WHERE username = ? ";

        jdbc.update(sql, role, user.getUsername());

    }

    public void setNumAllenamenti(User user){
        int allenamenti= userContaAllenamenti(user.getUsername());

        user.setAllenamentiUsufruiti(allenamenti);

        String sql = "UPDATE USERDATA SET numAllenamenti = ? where username =? ";
        jdbc.update(sql, allenamenti, user.getUsername());
    }

}
