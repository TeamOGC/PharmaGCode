package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.Utente;

import java.sql.*;

public class DBMSDaemon {
    private static final String baseUrl = "beverlylab.duckdns.org";
    private static final int port = 11051;
    private static final String user = "ogcadmin";
    private static final String pass = "OggiCarbonara";
    private static final String DBFarmacie = "DB_Farmacie";
    private static final String DBAzienda = "DB_Azienda";
    private static Connection connFarmacia = null;
    private static Connection connAzienda = null;

    public DBMSDaemon() {
        this.connect();
    }


    private static String buildConnectionUrl(String dbName) {
        if (dbName.equals(DBFarmacie) || dbName.equals(DBAzienda))
            return "jdbc:mariadb://" + baseUrl + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
        return "";
    }

    public void connect() {
        connectFarmacia();
        connectAzienda();
    }

    public void connectFarmacia() {
        try {
            if (connFarmacia == null || connFarmacia.isClosed())
                DBMSDaemon.connFarmacia = DriverManager.getConnection(buildConnectionUrl(DBFarmacie));
        } catch (java.sql.SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void connectAzienda() {
        try {
            if (connAzienda == null || connAzienda.isClosed())
                DBMSDaemon.connAzienda = DriverManager.getConnection(buildConnectionUrl(DBAzienda));
        } catch (java.sql.SQLException e) {
            e.printStackTrace(System.err);
        }
    }


// -- Query Farmacia

    /**
     * Controlla Credenziali utenza farmacista
     *
     * @param mail Mail dell'utente
     * @param password Password dell'utente (in chiaro)
     * @return {@link Utente}
     */
    public Utente F_ControllaCredenziali(String mail, String password) {
        connectFarmacia();
        var query = "SELECT Farmacista.* FROM DB_Farmacie.Farmacista WHERE email = ? and password = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password); // In futuro da hashare questa password
            var r = stmt.executeQuery();
            if (r.next()) {
                return new Utente(
                        r.getInt(1),
                        r.getInt(2),
                        r.getString(3),
                        r.getString(4),
                        r.getString(5),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return null;

    }



    public void testQuery() {
        connectFarmacia(); // provo a riconnettermi ad ogni query, metti caso che parte la connessione per qualche motivo
        var query = "SELECT Farmaco.* FROM DB_Farmacie.Farmaco WHERE Farmaco.nome = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, "Oki");

            var r = stmt.executeQuery();
            while (r.next()) {
                System.out.println("Cercato Oki, trovato  " + r.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }


}
