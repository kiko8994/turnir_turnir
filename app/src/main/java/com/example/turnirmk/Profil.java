package com.example.turnirmk;

public class Profil {
    String ime, prezime, kontakt, email, id;

    public Profil() {

    }

    public Profil(String ime, String prezime, String kontakt, String email, String id) {
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.email = email;
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

}
