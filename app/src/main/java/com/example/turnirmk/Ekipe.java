package com.example.turnirmk;

public class Ekipe {
    private String idEkipe;
    private String imeEkipe;
    private String idTurnira;
    //private String voditelj;
    public Ekipe(){

    }

    public Ekipe(String idEkipe, String imeEkipe, String idTurnira) {
        this.idEkipe = idEkipe;
        this.imeEkipe = imeEkipe;
        this.idTurnira = idTurnira;
        //this.voditelj = voditelj;
    }

    public String getIdEkipe() {
        return idEkipe;
    }

    public String getImeEkipe() {
        return imeEkipe;
    }

    public String getIdTurnira() { return idTurnira; }

    //public String voditelj() { return voditelj; }
}
