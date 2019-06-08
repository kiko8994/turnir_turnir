package com.example.turnirmk;

public class Ekipe {
    private String idEkipe;
    private String imeEkipe;
    private String idTurnira;
    public Ekipe(){

    }

    public Ekipe(String idEkipe, String imeEkipe, String idTurnira) {
        this.idEkipe = idEkipe;
        this.imeEkipe = imeEkipe;
        this.idTurnira = idTurnira;
    }

    public String getIdEkipe() {
        return idEkipe;
    }

    public String getImeEkipe() {
        return imeEkipe;
    }

    public String getIdTurnira() { return idTurnira; }
}
