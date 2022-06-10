package com.ogc.pharmagcode.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Ordine {
    private final int id_ordine;
    private final int id_farmaco;
    private final String nome_farmaco;
    private final int id_farmacia;
    private LocalDate data_consegna;
    private String stato;
    private int quantita;


    public Ordine(int id_ordine, int id_farmaco, String nome_farmaco, int id_farmacia, LocalDate data_consegna, String stato, int quantita) {
        this.id_ordine = id_ordine;
        this.id_farmaco = id_farmaco;
        this.nome_farmaco = nome_farmaco;
        this.id_farmacia = id_farmacia;
        this.data_consegna = data_consegna;
        this.stato = stato;
        this.quantita = quantita;
    }

    public Ordine(int id_ordine, Ordine ordine) {
        this.id_ordine = id_ordine;
        this.id_farmaco = ordine.getId_farmaco();
        this.nome_farmaco = ordine.getNome_farmaco();
        this.id_farmacia = ordine.getId_farmacia();
        this.data_consegna = ordine.getData_consegna();
        this.quantita = ordine.getQuantita();
        this.stato = ordine.getStato();
    }

    public int getQuantita() {
        return quantita;
    }

    public int getId_ordine() {
        return id_ordine;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public String getNome_farmaco() {
        return nome_farmaco;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    public LocalDate getData_consegna() {
        return data_consegna;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public static Ordine createFromDB(ResultSet r) throws SQLException {
        int id_ordine = r.getInt(1);
        int id_farmaco = r.getInt(2);
        int id_farmacia = r.getInt(3);
        LocalDate data_consegna = r.getDate(4).toLocalDate();
        int quantita = r.getInt(5);
        String stato = r.getString(6);
        String nome_farmaco = r.getString(7);
        Ordine ord = new Ordine(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        return ord;
    }
}
