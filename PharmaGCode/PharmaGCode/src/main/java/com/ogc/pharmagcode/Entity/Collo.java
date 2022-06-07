package com.ogc.pharmagcode.Entity;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collo {

    private final int id_collo;
    private final int id_farmacia;
    private ArrayList<Ordine> ordini = new ArrayList<>();
    private final LocalDate data_consegna;

    private SimpleStringProperty firma = new SimpleStringProperty();

    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, Ordine... ordini){
        this(id_collo, id_farmacia, data_consegna, ordini);
        this.firma.set(firma);
    }
    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna, Ordine... ordini){
        this.id_collo=id_collo;
        this.id_farmacia=id_farmacia;
        this.data_consegna=data_consegna;
        this.ordini.addAll(Arrays.asList(ordini));
    }
    public Collo(int id_collo, int id_farmacia, LocalDate data_consegna){
        this.id_collo=id_collo;
        this.id_farmacia=id_farmacia;
        this.data_consegna=data_consegna;
    }

    public void aggiungiOrdine(Ordine ordine){
        this.ordini.add(ordine);
    }
    public void aggiungiOrdini(Ordine... ordine){
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
        return firma.get();
    }

    public void setFirma(String firma) {
        this.firma.set(firma);
    }

    public static Collo createFromDB(ResultSet row) throws SQLException {
        return new Collo(
                row.getInt(1),
                row.getInt(2),
                row.getDate(3).toLocalDate(),
                row.getString(4)
        );
    }
}
