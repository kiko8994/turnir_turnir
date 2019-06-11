package com.example.turnirmk;

public class Ekipe {
    private String idEkipe;
    private String imeEkipe;
    private String voditelj;
    private String idTurnira;

    public Ekipe(){

    }

    public Ekipe(String idEkipe, String imeEkipe, String voditelj, String idTurnira) {
        this.idEkipe = idEkipe;
        this.imeEkipe = imeEkipe;
        this.voditelj = voditelj;
        this.idTurnira = idTurnira;
    }

    public String getIdEkipe() {
        return idEkipe;
    }

    public void setIdEkipe(String idEkipe) {
        this.idEkipe = idEkipe;
    }

    public String getImeEkipe() {
        return imeEkipe;
    }

    public void setImeEkipe(String imeEkipe) {
        this.imeEkipe = imeEkipe;
    }

    public String getVoditelj() {
        return voditelj;
    }

    public void setVoditelj(String voditelj) {
        this.voditelj = voditelj;
    }

    public String getIdTurnira() {
        return idTurnira;
    }

    public void setIdTurnira(String idTurnira) {
        this.idTurnira = idTurnira;
    }
}
