package gym_in_tech.webapp_14.pojos;

public class Recensione {
    private int id;
    private String username;
    private String recensione;

    public Recensione() {
        id=0;
        username="";
        recensione="";
    }

    public Recensione(String username, String recensione) {
        id=0;
        this.username = username;
        this.recensione = recensione;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRecensione() { return recensione; }
    public void setRecensione(String recensione) { this.recensione = recensione; }
}
