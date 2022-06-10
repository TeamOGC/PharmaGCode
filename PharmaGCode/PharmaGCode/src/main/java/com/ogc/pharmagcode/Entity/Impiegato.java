package com.ogc.pharmagcode.Entity;

import com.ogc.pharmagcode.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Impiegato extends Utente {

    private final int id_impiegato;

    public Impiegato(int id_impiegato, String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
        this.id_impiegato = id_impiegato;
    }

    public int getId_impiegato() {
        return id_impiegato;
    }

    public static Impiegato createFromDB(ResultSet row) {
        try {
            return new Impiegato(
                    row.getInt(1),
                    row.getString(2),
                    row.getString(3),
                    row.getString(4),
                    row.getString(5));
        } catch (SQLException e) {
            Main.log.error("Errore durante conversione DB->Utente", e);
        }
        return null;
    }
}
