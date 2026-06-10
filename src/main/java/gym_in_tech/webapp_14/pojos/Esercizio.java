package gym_in_tech.webapp_14.pojos;

public class Esercizio {
    private int id;
    private String nomeEs;
    private int numSerie;
    private int numRipetizioni;

    public Esercizio(){
        this.id =0;
        this.nomeEs="";
        this.numSerie=0;
        this.numRipetizioni=0;
    }

    public Esercizio(String nomeEs, int numSerie, int numRipetizioni){
        this.id =0;
        this.nomeEs=nomeEs;
        this.numSerie=numSerie;
        this.numRipetizioni=numRipetizioni;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNomeEs() { return nomeEs; }
    public void setNomeEs(String nomeEs) { this.nomeEs = nomeEs; }

    public int getNumSerie() { return numSerie; }
    public void setNumSerie(int numSerie) { this.numSerie = numSerie; }

    public int getNumRipetizioni() { return numRipetizioni; }
    public void setNumRipetizioni(int numRipetizioni) { this.numRipetizioni = numRipetizioni; }

}
