package gym_in_tech.webapp_14.pojos;

import java.util.ArrayList;
import java.util.List;

public class Programma {

    private int id;
    private String nomeProg;
    private List<Esercizio> esercizi;
    private int numAllenamenti;
    private float kcal;

    public Programma() {
        this.nomeProg = "";
        this.numAllenamenti=0;
        this.tipo="";
        this.kcal=0;
        this.esercizi = new ArrayList<>();
    }

    public Programma(String nomeProg, List<Esercizio> esercizi, float kcal){
        this.nomeProg = nomeProg;
        this.numAllenamenti=0;
        this.esercizi = esercizi;
        this.kcal=kcal;
    }

    public void aggiungiEsercizio(Esercizio e) {
        esercizi.add(e);
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public String getNomeProg() {
        return nomeProg;
    }

    public void setNomeProg(String nomeProg) {
        this.nomeProg = nomeProg;
    }

    public float getKcal() { return kcal; }
    public void setKcal(float kcal) { this.kcal = kcal; }

    public int getNumAllenamenti() { return numAllenamenti; }
    public void setNumAllenamenti(int numAllenamenti) { this.numAllenamenti = numAllenamenti; }

}
