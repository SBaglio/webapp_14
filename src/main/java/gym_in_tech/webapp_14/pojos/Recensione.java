package gym_in_tech.webapp_14.pojos;

public class Recensione {
    private int id;
    private String nome;
    private String recensione;

    public Recensione() {
        id=0;
        nome="";
        recensione="";
    }

    public Recensione(String nome, String recensione) {
        id=0;
        this.nome = nome;
        this.recensione = recensione;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRecensione() { return recensione; }
    public void setRecensione(String recensione) { this.recensione = recensione; }
}
