package com.example.turnirmk;

public class Profil {
    String ime, prezime, kontakt, username, ekipa;

    public Profil() {

    }

    public Profil(String ime, String prezime, String kontakt, String username, String ekipa) {
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.username = username;
        this.ekipa = ekipa;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKontakt() {
        return kontakt;
    }

    public String getUsername() {
        return username;
    }

    public String getEkipa() { return ekipa; }

}
