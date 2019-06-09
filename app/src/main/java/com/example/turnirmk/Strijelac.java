package com.example.turnirmk;

public class Strijelac {
    String imeStrijelca,momcad;
    int brojGolova;

    public Strijelac() {
    }

    public Strijelac(String imeStrijelca, String momcad, int brojGolova) {
        this.imeStrijelca = imeStrijelca;
        this.momcad = momcad;
        this.brojGolova = brojGolova;
    }

    public String getImeStrijelca() {
        return imeStrijelca;
    }

    public String getMomcad() {
        return momcad;
    }

    public int getBrojGolova() {
        return brojGolova;
    }
}
