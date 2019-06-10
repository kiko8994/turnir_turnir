package com.example.turnirmk;

public class Strijelac {
    String imeStrijelca,momcad, idStrijelca;
    int brojGolova;

    public Strijelac() {
    }

    public Strijelac(String imeStrijelca, String momcad, String idStrijelca, int brojGolova) {
        this.imeStrijelca = imeStrijelca;
        this.momcad = momcad;
        this.idStrijelca = idStrijelca;
        this.brojGolova = brojGolova;
    }

    public String getImeStrijelca() {
        return imeStrijelca;
    }

    public String getMomcad() {
        return momcad;
    }

    public String getIdStrijelca() {
        return idStrijelca;
    }

    public int getBrojGolova() {
        return brojGolova;
    }
}
