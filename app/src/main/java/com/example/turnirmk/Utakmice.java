package com.example.turnirmk;

public class Utakmice {
    String tekma, strijelci, rezultat;

    public Utakmice() {

    }

    public Utakmice(String tekma, String strijelci, String rezultat) {
        this.tekma = tekma;
        this.strijelci = strijelci;
        this.rezultat = rezultat;
    }

    public String getTekma() {
        return tekma;
    }

    public String getStrijelci() {
        return strijelci;
    }

    public String getRezultat() {
        return rezultat;
    }
}
