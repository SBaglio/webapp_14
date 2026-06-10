package gym_in_tech.webapp_14.repositories;

import gym_in_tech.webapp_14.pojos.Recensione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RecensioneRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public RecensioneRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Recensione recensione) {
        String sql = "INSERT INTO RECENSIONI (username, recensione) VALUES (?, ?)";
        jdbc.update(sql, recensione.getUsername(), recensione.getRecensione());
    }

    public List<Recensione> findAll() {
        String sql = "SELECT * FROM RECENSIONI ORDER BY id DESC";

        RowMapper<Recensione> recensioneRowMapper = (rs, rowNum) -> {
            Recensione r = new Recensione();
            r.setId(rs.getInt("id"));
            r.setUsername(rs.getString("username"));
            r.setRecensione(rs.getString("recensione"));
            return r;
        };

        return jdbc.query(sql, recensioneRowMapper);
    }
}
