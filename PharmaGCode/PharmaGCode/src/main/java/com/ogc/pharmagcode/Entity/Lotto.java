package com.ogc.pharmagcode.Entity;

public class Lotto {
    private final int id_farmaco;
    private final int id_lotto;

    private int quantita;

    public Lotto(int id_farmaco, int id_lotto, int quantita) {
        this.id_farmaco = id_farmaco;
        this.id_lotto = id_lotto;
        this.quantita = quantita;
    }

    public Lotto(Lotto l) {
        this.id_farmaco = l.getId_farmaco();
        this.id_lotto = l.getId_lotto();
        this.quantita = l.getQuantita();
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public int getId_lotto() {
        return id_lotto;
    }
}
