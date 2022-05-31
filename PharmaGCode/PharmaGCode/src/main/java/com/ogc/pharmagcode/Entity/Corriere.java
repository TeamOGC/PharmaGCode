package com.ogc.pharmagcode.Entity;

import com.ogc.pharmagcode.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Corriere extends Utente{

    private final int id_corriere;

    public Corriere(int id_corriere, String nome, String cognome, String email, String password ){
        super(nome, cognome, email, password);
        this.id_corriere=id_corriere;
    }

    public int getId_corriere(){
        return id_corriere;
    }

    public static Corriere createFromDB(ResultSet row) {
        try {
            return new Corriere(
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
