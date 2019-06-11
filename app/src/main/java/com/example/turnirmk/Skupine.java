package com.example.turnirmk;

public class Skupine {
    String ekipaJedan, ekipaDva, ekipaTri, ekipaCetiri, idGrupe;

    public Skupine() {
    }

    public Skupine(String ekipaJedan, String ekipaDva, String ekipaTri, String ekipaCetiri, String idGrupe) {
        this.ekipaJedan = ekipaJedan;
        this.ekipaDva = ekipaDva;
        this.ekipaTri = ekipaTri;
        this.ekipaCetiri = ekipaCetiri;
        this.idGrupe = idGrupe;
    }

    public String getEkipaJedan() {
        return ekipaJedan;
    }

    public String getEkipaDva() {
        return ekipaDva;
    }

    public String getEkipaTri() {
        return ekipaTri;
    }

    public String getEkipaCetiri() {
        return ekipaCetiri;
    }

    public String getIdGrupe() {
        return idGrupe;
    }
}
