package com.ogc.pharmagcode.Utils;

import java.io.PrintStream;
import java.sql.*;
import java.util.Properties;

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

    public static String buildConnectionUrl(String dbName) {
        if (dbName.equals(DBFarmacie) || dbName.equals(DBAzienda))
            return "jdbc:mariadb://" + baseUrl + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
        return "";
    }

    public void connect() {
        try {
            if (connFarmacia == null || connFarmacia.isClosed())
                DBMSDaemon.connFarmacia = DriverManager.getConnection(buildConnectionUrl(DBFarmacie));
        } catch (java.sql.SQLException e) {
            e.printStackTrace(System.err);
        }
        try {
            if (connAzienda == null || connAzienda.isClosed())
                    DBMSDaemon.connAzienda = DriverManager.getConnection(buildConnectionUrl(DBAzienda));
        } catch (java.sql.SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    public void testQuery(){
        connect(); // provo a riconnettermi ad ogni query, metti caso che parte la connessione per qualche motivo
        var query = "SELECT Farmaco.* FROM DB_Farmacie.Farmaco WHERE Farmaco.nome = ?";
        try(PreparedStatement stmt = connFarmacia.prepareStatement(query)){
            stmt.setString(1, "Oki");

            var r = stmt.executeQuery();
            while(r.next()){
                System.out.println("Cercato Oki, trovato  " + r.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace(System.err);
        }
    }


}
