package gym_in_tech.webapp_14.repositories;

import gym_in_tech.webapp_14.pojos.Esercizio;
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

//    public List<Programma> findAllPrograms() {
//        String sql = "SELECT * FROM PROGRAMMA";
//        RowMapper<Programma> programmaRowMapper = (r, i) -> {
//            Programma rowObject = new Programma();
//            rowObject.setId(r.getInt("ID"));
//            rowObject.setNomeProg(r.getString("NOMEES"));
//            rowObject.setKcal(r.getInt("KCAL"));
//            return rowObject;
//        };
//        return jdbc.query(sql,programmaRowMapper);
//    }

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


    public List<Programma> findUserPrograms(String username) {
        String sql = "SELECT id, nome, kcal FROM USER_PRO_PROGRAMMI where username=?";

        RowMapper<Programma> programmaRowMapper = (r, i) -> {
            Programma rowObject = new Programma();
            rowObject.setId(r.getInt("ID"));
            rowObject.setNomeProg(r.getString("NOME"));
            rowObject.setEsercizi(getComposizione(rowObject.getNomeProg()));
            rowObject.setKcal(r.getInt("KCAL"));

            return rowObject;
        };
        return jdbc.query(sql, programmaRowMapper, username);
    }

    public List<Esercizio> getComposizione(String nomeProgramma) {
        String sql = """
            SELECT e.nomeEs, e.numSerie, e.numRep, e.kcal
            FROM ESERCIZI e
            JOIN PROGRAMMI_REST p ON e.idProgramma = p.id
            WHERE p.nome = ?
            """;
        return jdbc.query(sql, (r, i) -> {
            Esercizio e = new Esercizio();
            e.setNomeEs(r.getString("NOMEES"));
            e.setNumSerie(r.getInt("NUMSERIE"));
            e.setNumRipetizioni(r.getInt("NUMREP"));

            return e;
        }, nomeProgramma);
    }



}
