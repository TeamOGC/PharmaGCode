package com.ogc.pharmagcode.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdinePeriodico {

    private final int id_farmacia;
    private final int id_farmaco;
    private int quantita;
    private final int periodicita;

    public OrdinePeriodico(int id_farmacia, int id_farmaco, int quantita, int periodicita){
        this.id_farmacia=id_farmacia;
        this.id_farmaco=id_farmaco;
        this.quantita=quantita;
        this.periodicita=periodicita;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    public int getId_farmaco() {
        return id_farmaco;
    }

    public int getQuantita() {
        return quantita;
    }

    public int getPeriodicita() {
        return periodicita;
    }

    public void setQuantita(int qty){
        this.quantita=quantita;
    }

    public static OrdinePeriodico createFromDB(ResultSet row) throws SQLException {
        int id_farmacia = row.getInt(1);
        int id_farmaco = row.getInt(2);
        int quantita = row.getInt(3);
        int periodicita = row.getInt(4);
        return new OrdinePeriodico(id_farmacia, id_farmaco, quantita, periodicita);
    }
}
