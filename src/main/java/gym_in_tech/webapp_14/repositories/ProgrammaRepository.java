package gym_in_tech.webapp_14.repositories;

import gym_in_tech.webapp_14.pojos.Programma;
import gym_in_tech.webapp_14.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ProgrammaRepository{
    private final JdbcTemplate jdbc;
    private final UserDetailsManager userDetailsManager;

    @Autowired
    public ProgrammaRepository(JdbcTemplate jdbc, UserDetailsManager userDetailsManager){
        this.jdbc = jdbc;
        this.userDetailsManager = userDetailsManager;
    }

    public List<Programma> findAllPrograms() {
        String sql = "SELECT * FROM PROGRAMMA";
        RowMapper<Programma> programmaRowMapper = (r, i) -> {
            Programma rowObject = new Programma();
            rowObject.setId(r.getInt("ID"));
            rowObject.setNomeProg(r.getString("NOMEES"));
            rowObject.setKcal(r.getInt("KCAL"));
            return rowObject;
        };
        return jdbc.query(sql,programmaRowMapper);
    }

    @Transactional
    public void addPrograms(Programma programma) {
        String sql = "INSERT INTO PROGRAMMA (NomeEs, NumSerie, Kcal) VALUES ( ?, ?, ?)";
        jdbc.update(sql,
                programma.getNomeProg(),
                programma.getKcal()
                );
    }

    public Programma getProgramma(String NomeProgramma){
        String sql = "SELECT * FROM USERDATA where NomeProgramma= ?";
        RowMapper<Programma> programmaRowMapper = (r, i) -> {
            Programma rowObject = new Programma();
            rowObject.setId(r.getInt("ID"));
            rowObject.setKcal(r.getInt("KCAL"));
            return rowObject;
        };
        return jdbc.queryForObject(sql, programmaRowMapper, NomeProgramma);
    }


}
