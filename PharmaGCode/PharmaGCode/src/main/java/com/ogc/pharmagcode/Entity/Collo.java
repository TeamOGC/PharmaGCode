package com.ogc.pharmagcode.Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Collo {

    private final int id_farmacia;
    private ArrayList<Ordine> ordini;
    private final LocalDate data_consegna;

    public Collo(int id_farmacia, LocalDate data_consegna, ArrayList<Ordine> ordini){
        this.id_farmacia=id_farmacia;
        this.data_consegna=data_consegna;
        this.ordini=ordini;

    }
    public Collo(int id_farmacia, LocalDate data_consegna){
        this.id_farmacia=id_farmacia;
        this.data_consegna=data_consegna;
        this.ordini= new ArrayList<>();
    }

    public void aggiungiOrdine(Ordine ordine){
        this.ordini.add(ordine);
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
}
