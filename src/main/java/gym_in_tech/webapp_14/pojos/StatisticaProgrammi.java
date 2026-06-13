package gym_in_tech.webapp_14.pojos;

public class StatisticaProgrammi {
    private String nomeProgramma;
    private String ruolo;
    private double media;
    private int conteggio;

    public StatisticaProgrammi(){
        nomeProgramma="";
        ruolo="";
        media=0;
        conteggio=0;
    }

    public StatisticaProgrammi(String nomeProgramma, String ruolo, double media, int conteggio){
        this.nomeProgramma=nomeProgramma;
        this.ruolo=ruolo;
        this.media=media;
        this.conteggio=conteggio;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getMedia() {
        return media;
    }

    public void setNomeProgramma(String nomeProgramma) {
        this.nomeProgramma = nomeProgramma;
    }

    public String getNomeProgramma(){
        return this.nomeProgramma;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setConteggio(int conteggio) {
        this.conteggio = conteggio;
    }

    public int getConteggio() {
        return conteggio;
    }
}
