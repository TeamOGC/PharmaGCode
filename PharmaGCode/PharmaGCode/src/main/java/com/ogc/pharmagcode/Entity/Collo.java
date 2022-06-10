package com.ogc.pharmagcode.Entity;

import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Collo {

    private final int id_collo;
    private final int id_farmacia;
    private String nome_farmacia = "";
    private String indirizzo_farmacia = "";

    private ArrayList<Ordine> ordini = new ArrayList<>();
    private final LocalDate data_consegna;

    private SimpleStringProperty firma = new SimpleStringProperty("");

    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, String nome_farmacia, String indirizzo_farmacia, Ordine... ordini) {
        this(id_collo, id_farmacia, data_consegna, ordini);
        this.firma.set(firma);
        this.nome_farmacia = nome_farmacia;
        this.indirizzo_farmacia = indirizzo_farmacia;
    }

    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna, Ordine... ordini) {
        this.id_collo = id_collo;
        this.id_farmacia = id_farmacia;
        this.data_consegna = data_consegna;
        this.ordini.addAll(Arrays.asList(ordini));
    }

    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna) {
        this.id_collo = id_collo;
        this.id_farmacia = id_farmacia;
        this.data_consegna = data_consegna;
    }

    public void aggiungiOrdine(Ordine ordine) {
        this.ordini.add(ordine);
    }

    public void aggiungiOrdini(Ordine... ordine) {
        this.ordini.addAll(Arrays.asList(ordine));
    }

    public int getId_collo() {
        return id_collo;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public LocalDate getData_consegna() {
        return data_consegna;
    }

    public String getFirma() {
        return firma.get() == null ? "" : firma.get();
    }

    public void setFirma(String firma) {
        this.firma.set(firma);
    }

    public void setNome_farmacia(String nome_farmacia) {
        this.nome_farmacia = nome_farmacia;
    }

    public void setIndirizzo_farmacia(String indirizzo_farmacia) {
        this.indirizzo_farmacia = indirizzo_farmacia;
    }

    public String getNome_farmacia() {
        return nome_farmacia;
    }

    public String getIndirizzo_farmacia() {
        return indirizzo_farmacia;
    }

    /**
     * Converte i risultati di una query con il seguente SELECT
     * SELECT Collo.*, Farmacia.nome, Farmacia.indirizzo
     *
     * @param row risultati della query
     * @return Collo corrispondente
     * @throws SQLException
     */
    public static Collo createFromDB(ResultSet row) throws SQLException {
        return new Collo(
                row.getInt(1),
                row.getInt(2),
                row.getDate(3).toLocalDate(),
                row.getString(4),
                row.getString(5),
                row.getString(6)
        );
    }
}
