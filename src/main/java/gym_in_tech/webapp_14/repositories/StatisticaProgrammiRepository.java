package gym_in_tech.webapp_14.repositories;

import gym_in_tech.webapp_14.pojos.Programma;
import gym_in_tech.webapp_14.pojos.StatisticaProgrammi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticaProgrammiRepository {
    private final JdbcTemplate jdbc;
    private final UserDetailsManager userDetailsManager;

    @Autowired
    public StatisticaProgrammiRepository(JdbcTemplate jdbc, UserDetailsManager userDetailsManager){
        this.jdbc = jdbc;
        this.userDetailsManager = userDetailsManager;
    }

    public List<StatisticaProgrammi> getMedieEsecuzioniPerRuolo(List<Programma> programmiPredefiniti) {

        String sql = """
            SELECT a.authority AS ruolo,
                   AVG(COALESCE(s.numEsecuzioni, 0)) AS media
            FROM AUTHORITIES a
            LEFT JOIN STATISTICHE_PROGRAMMI s
                ON s.username = a.username AND s.nomeProgramma = ?
            WHERE a.authority IN ('ROLE_USER_BASIC', 'ROLE_USER_PRO')
            GROUP BY a.authority
            """;

        List<StatisticaProgrammi> risultati = new ArrayList<>();

        for (Programma programma : programmiPredefiniti) {
            String nomeProgramma = programma.getNomeProg();
            List<StatisticaProgrammi> parziali = jdbc.query(sql, (r, i) -> {
                StatisticaProgrammi s = new StatisticaProgrammi();
                s.setNomeProgramma(nomeProgramma);
                s.setRuolo(r.getString("ruolo"));
                s.setMedia(Math.round(r.getDouble("media") * 1000.0) / 1000.0);
                return s;
            }, nomeProgramma);

            risultati.addAll(parziali);
        }

        return risultati;
    }


    public void incrementaEsecuzione(String username, String nomeProgramma) {
        String sqlCheck = "SELECT numEsecuzioni FROM STATISTICHE_PROGRAMMI WHERE username = ? AND nomeProgramma = ?";

        List<Integer> result = jdbc.query(sqlCheck, (r, i) -> r.getInt("numEsecuzioni"), username, nomeProgramma);

        if (result.isEmpty()) {
            // Prima esecuzione: crea la riga
            jdbc.update("INSERT INTO STATISTICHE_PROGRAMMI (username, nomeProgramma, numEsecuzioni) VALUES (?, ?, 1)", username, nomeProgramma);
        } else {
            // Esecuzioni successive: incrementa
            jdbc.update("UPDATE STATISTICHE_PROGRAMMI SET numEsecuzioni = numEsecuzioni + 1 WHERE username = ? AND nomeProgramma = ?", username, nomeProgramma);
        }
    }

    public List<StatisticaProgrammi> getStatisticheUtente(String username) {
        String sql = "SELECT nomeProgramma, numEsecuzioni FROM STATISTICHE_PROGRAMMI WHERE username = ?";
        return jdbc.query(sql, (r, i) -> {
            StatisticaProgrammi s = new StatisticaProgrammi();
            s.setNomeProgramma(r.getString("nomeProgramma"));
            s.setConteggio(r.getInt("numEsecuzioni"));
            return s;
        }, username);
    }

    public List<StatisticaProgrammi> getStatistichePersonalizzati(String username) {
        String sql = "SELECT nome AS nomeProgramma, numAllenamenti AS conteggio FROM USER_PRO_PROGRAMMI WHERE username = ?";
        return jdbc.query(sql, (r, i) -> {
            StatisticaProgrammi s = new StatisticaProgrammi();
            s.setNomeProgramma(r.getString("nomeProgramma"));
            s.setConteggio(r.getInt("conteggio"));
            return s;
        }, username);
    }

}
