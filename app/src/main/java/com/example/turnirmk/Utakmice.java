package com.example.turnirmk;

public class Utakmice {
    String id, tekma, rezultat;

    public Utakmice() {
    }

    public Utakmice(String id, String tekma, String rezultat) {
        this.id = id;
        this.tekma = tekma;
        this.rezultat = rezultat;
    }

    public String getId() {
        return id;
    }

    public String getTekma() {
        return tekma;
    }

    public String getRezultat() {
        return rezultat;
    }
}
