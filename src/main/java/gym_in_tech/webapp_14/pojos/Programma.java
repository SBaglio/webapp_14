package gym_in_tech.webapp_14.pojos;

import org.springframework.context.annotation.Bean;

public class Programma {

    private int id;
    private String nomeProgramma;
    private String nomeEs;
    private int numSerie;
    private int numRipetizioni;
    private float kcal;
    private int numAllenamenti;
    private String tipo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeProgramma() { return nomeProgramma; }
    public void setNomeProgramma(String nomeProgramma) { this.nomeProgramma = nomeProgramma; }

    public String getNomeEs() { return nomeEs; }
    public void setNomeEs(String nomeEs) { this.nomeEs = nomeEs; }

    public int getNumSerie() { return numSerie; }
    public void setNumSerie(int numSerie) { this.numSerie = numSerie; }

    public int getNumRipetizioni() { return numRipetizioni; }
    public void setNumRipetizioni(int numRipetizioni) { this.numRipetizioni = numRipetizioni; }

    public float getKcal() { return kcal; }
    public void setKcal(float kcal) { this.kcal = kcal; }

    public int getNumAllenamenti() { return numAllenamenti; }
    public void setNumAllenamenti(int numAllenamenti) { this.numAllenamenti = numAllenamenti; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
