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

    @Autowired
    public ProgrammaRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Transactional
    public long aggiungiProgramma(Programma programma, String username) {
        String sql = "INSERT INTO USER_PRO_PROGRAMMI (nome, username, kcal, numAllenamenti) VALUES ( ?, ?, ?, ?)";
        jdbc.update(sql,
                programma.getNomeProg(),
                username,
                programma.getKcal(),
                0
                );

        // Recupera l'id appena creato - Possiamo usare MAX perche' gli id vengono generati in maniera crescente
        String sqlId = "SELECT MAX(id) FROM USER_PRO_PROGRAMMI WHERE username = ? AND nome = ?";
        return jdbc.queryForObject(sqlId, Long.class, username, programma.getNomeProg());
    }

    @Transactional
    public void aggiungiEsercizio(long idProg, Esercizio es){
        String sql = "INSERT INTO ESERCIZI_PROGRAMMI_PERSONALIZZATI (idProg, nomeEs, numSerie, numRep) VALUES (?, ?, ?, ?)";

        jdbc.update(sql, idProg, es.getNomeEs(), es.getNumSerie(), es.getNumRipetizioni());
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
            rowObject.setEsercizi(getComposizione(rowObject.getId()));
            rowObject.setKcal(r.getInt("KCAL"));

            return rowObject;
        };
        return jdbc.query(sql, programmaRowMapper, username);
    }

    public List<Esercizio> getComposizione(long idProg) {
        String sql = "SELECT nomeEs, numSerie, numRep FROM ESERCIZI_PROGRAMMI_PERSONALIZZATI where idProg = ? ";
        return jdbc.query(sql, (r, i) -> {
            Esercizio e = new Esercizio();
            e.setNomeEs(r.getString("NOMEES"));
            e.setNumSerie(r.getInt("NUMSERIE"));
            e.setNumRipetizioni(r.getInt("NUMREP"));

            return e;
        }, idProg);
    }

    public void incrementaAllenamentiPersonalizzato(String username, String nomeProgramma){
        String sql = "UPDATE USER_PRO_PROGRAMMI SET numAllenamenti = numAllenamenti + 1 WHERE username = ? AND nome = ?";
        jdbc.update(sql, username, nomeProgramma);
    }

}
