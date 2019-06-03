package com.example.turnirmk;

public class Dogadaj {
    String imeDogadaja;
    String kontakt;
    String lokacija;
    String idDogadaja;
    String datum;

    public Dogadaj() {
    }

    public Dogadaj(String imeDogadaja, String kontakt, String lokacija, String idDogadaja, String datum) {
        this.imeDogadaja = imeDogadaja;
        this.kontakt = kontakt;
        this.lokacija = lokacija;
        this.idDogadaja = idDogadaja;
        this.datum = datum;
    }

    public String getImeDogadaja() {
        return imeDogadaja;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getLokacija() {
        return lokacija;
    }

    public String getIdDogadaja() {
        return idDogadaja;
    }

    public String getDatum() {
        return datum;
    }
}