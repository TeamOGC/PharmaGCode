package com.ogc.pharmagcode.Entity;

import com.ogc.pharmagcode.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Farmacista extends Utente {
    private final int id_farmacista;
    private final int id_farmacia;

    public Farmacista(int id_farmacista, int id_farmacia, String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
        this.id_farmacia = id_farmacia;
        this.id_farmacista = id_farmacista;
    }

    public Farmacista(int id_farmacista, int id_farmacia, String nome, String cognome, String email) {
        super(nome, cognome, email);
        this.id_farmacia = id_farmacia;
        this.id_farmacista = id_farmacista;
    }

    public int getId_farmacista() {
        return id_farmacista;
    }

    public int getId_farmacia() {
        return id_farmacia;
    }

    public static Farmacista createFromDB(ResultSet row) {
        try {
            return new Farmacista(
                    row.getInt(1),
                    row.getInt(2),
                    row.getString(3),
                    row.getString(4),
                    row.getString(5)
            );
        } catch (SQLException e) {
            Main.log.error("Errore durante conversione DB->Utente", e);
        }
        return null;
    }
}
