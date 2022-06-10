package com.ogc.pharmagcode.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdinePeriodico {

    private final int id_farmacia;

    private final String nomeFarmaco;
    private final String nomeFarmacia;
    private final int id_farmaco;

    private int quantita;
    private final int periodicita;

    public OrdinePeriodico(int id_farmacia, int id_farmaco, int quantita, int periodicita, String nomeFarmaco, String nomeFarmacia) {
        this.id_farmacia = id_farmacia;
        this.id_farmaco = id_farmaco;
        this.quantita = quantita;
        this.periodicita = periodicita;
        this.nomeFarmaco = nomeFarmaco;
        this.nomeFarmacia = nomeFarmacia;
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

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    public String getNomeFarmacia() {
        return nomeFarmacia;
    }

    public static OrdinePeriodico createFromDB(ResultSet row) throws SQLException {
        int id_farmacia = row.getInt(1);
        int id_farmaco = row.getInt(2);
        int quantita = row.getInt(3);
        int periodicita = row.getInt(4);
        String nomeFarmaco = row.getString(5);
        String nomeFarmacia = row.getString(6);
        return new OrdinePeriodico(id_farmacia, id_farmaco, quantita, periodicita, nomeFarmaco, nomeFarmacia);
    }

    @Override
    public String toString() {
        return "OrdinePeriodico{" +
                "id_farmacia=" + id_farmacia +
                ", nomeFarmaco='" + nomeFarmaco + '\'' +
                ", nomeFarmacia='" + nomeFarmacia + '\'' +
                ", id_farmaco=" + id_farmaco +
                ", quantita=" + quantita +
                ", periodicita=" + periodicita +
                '}';
    }
}
