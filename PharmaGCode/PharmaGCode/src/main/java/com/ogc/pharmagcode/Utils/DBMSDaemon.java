package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.Farmacista;
import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBMSDaemon {
    private static final String baseUrl = "beverlylab.duckdns.org";
    private static final int port = 11051;
    private static final String user = "ogcadmin";
    private static final String pass = "OggiCarbonara";
    private static final String DBFarmacie = "DB_Farmacie";
    private static final String DBAzienda = "DB_Azienda";
    private static Connection connFarmacia = null;
    private static Connection connAzienda = null;

    
    private static void erroreComunicazioneDBMS(Exception e) {
        Main.log.error("Errore durante comunicazione con DBMS", e);
        Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");
    }

    private static String buildConnectionUrl(String dbName) {
        if (dbName.equals(DBFarmacie) || dbName.equals(DBAzienda))
            return "jdbc:mariadb://" + baseUrl + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
        return "";
    }

    public static void connect() {
        connectFarmacia();
        connectAzienda();
    }

    public static void connectFarmacia() {
        try {
            if (connFarmacia == null || connFarmacia.isClosed()) {
                Main.log.debug("Connettendo con Farmacia...");
                DBMSDaemon.connFarmacia = DriverManager.getConnection(buildConnectionUrl(DBFarmacie));
                Main.log.info("Connesso con Farmacia");
            }
        } catch (java.sql.SQLException e) {
            erroreComunicazioneDBMS(e);
            
        }
    }

    public static void connectAzienda() {
        try {
            if (connAzienda == null || connAzienda.isClosed()) {
                Main.log.debug("Connettendo con Azienda...");
                DBMSDaemon.connAzienda = DriverManager.getConnection(buildConnectionUrl(DBAzienda));
                Main.log.info("Connesso con Azienda");
            }
        } catch (java.sql.SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


// -- Query Farmacia

    /**
     * Controlla Credenziali utenza farmacista
     *
     * @param mail Mail dell'utente
     * @param password Password dell'utente già hashata
     * @return {@link Utente}
     */
    public static Utente F_ControllaCredenziali(String mail, String password) {
        connectFarmacia();
        var query = "SELECT Farmacista.* FROM DB_Farmacie.Farmacista WHERE email = ? and password = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Farmacista.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;

    }

    /**
     *  Verifica l'esistenza di una mail all'interno del DBMS
     *
     * @param mail email dell'utente
     * @return true if email in DB, false if not
     *
     */
    public static boolean verificaEsistenzaMail(String mail){
        connectFarmacia();
        String query= "SELECT Farmacista.email FROM Farmacista WHERE email = ?";
        try(PreparedStatement stmt=connFarmacia.prepareStatement(query)){
            stmt.setString(1, mail);
            var r= stmt.executeQuery();
            if (r.next()){
                return true;
            }
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Aggiorna la password di un utente
     *
     * @param mail mail dell'utente
     * @param password password dell'utente
     * @return 1 if password aggiornata, -1 altrimenti
     */
    public static int aggiornaPassword(String mail, String password){
        connectFarmacia();
        String query= "UPDATE Farmacista SET Farmacista.password = ? WHERE Farmacista.email = ?";
        try(PreparedStatement stmt= connFarmacia.prepareStatement(query)){
            stmt.setString(2, mail);
            stmt.setString(1, password);
            var r= stmt.executeUpdate();
            if (r!=0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }
    /**
     * Controlla scorte farmacia dopo lo scarico merci
     *
     * @param id_lotto lotto del farmaco scaricato
     * @param id_farmacia farmacia che ha scaricato il farmaco
     * @return quantità ancora disponibili dalla farmacia
     */
    public static int controlloScorte(int id_lotto, int id_farmacia) {
        connectFarmacia();
        var query = "SELECT Lotto.quantita FROM DB_Farmacie.Lotto WHERE id_lotto = ? AND id_farmacia= ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, id_lotto);
            stmt.setInt(2, id_farmacia);
            var r = stmt.executeQuery();
            if(r.next()) {
                return r.getInt(1);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Consente di effettuare il caricamento merci
     *
     * @param id_lotto id del lotto
     * @param id_farmacia id della farmacia
     * @param data_caricamento data del caricamento della merce
     * @param qty quantita
     */
    public static void caricaFarmaco(int id_lotto, int id_farmacia, Date data_caricamento, int qty) {
        connectFarmacia();
        try (CallableStatement call = connFarmacia.prepareCall("{CALL caricoMerci(?, ?, ?, ?, ?)}")) {
            call.setInt(1, id_lotto);
            call.setInt(2, id_farmacia);
            call.setDate(3, data_caricamento);
            call.setInt(4, qty);
            call.execute();

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Consente di effettuare lo scarico di un farmaco appena venduto
     *
     * @param id_lotto id del lotto che viene scaricato
     * @param id_farmacia id della farmacia che effettua lo scarico
     * @param qty quantità di farmaco che viene scaricato
     * @return quantità di farmaco ancora disponibile in quella farmacia, -1 se ha errorato
     */
    public static int scaricaMerci(int id_lotto, int id_farmacia, int qty) {
        connectFarmacia();
        String query = "UPDATE DB_Farmacie.Lotto SET Lotto.quantita=((SELECT Lotto.quantita FROM DB_Farmacie.Lotto WHERE Lotto.id_lotto=?)-?) WHERE Lotto.id_lotto=? AND Lotto.id_farmacia=?";
        try (PreparedStatement stmt= connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, id_lotto);
            stmt.setInt(2, qty);
            stmt.setInt(3, id_lotto);
            stmt.setInt(4, id_farmacia);
            var r = stmt.executeUpdate();
            var squery = "SELECT Lotto.quantita FROM DB_Farmacie.Lotto WHERE Lotto.id_lotto=?";
            try (PreparedStatement sstmt= connFarmacia.prepareStatement(squery)) {
                sstmt.setInt(1, id_lotto);
                var risultato = sstmt.executeQuery();
                if (risultato.next()) {
                    return risultato.getInt(1);
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Query per chiedere la merce caricata in data odierna per l'avviso delle 20:00
     *
     * @param id_farmacia id della farmacia da cui parte la richiesta
     * @param data_caricamento data in cui parte la richiesta
     * @return "risultato" è un {@link ResultSet} che contiene nella colonna 1 l'id lotto e nella colonna 2 la quantità caricata
     */
    public static ResultSet queryMerceCaricata(int id_farmacia, Date data_caricamento) {
        connectFarmacia();
        String query = "SELECT Caricamenti.id_lotto, Caricamenti.quantita FROM Caricamenti WHERE Caricamenti.data_caricamento=? AND Caricamenti.id_farmacia=?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setDate(1, data_caricamento);
            stmt.setInt(2, id_farmacia);
            return stmt.executeQuery();
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Aggiorna la quantità di farmaci
     *
     * @param listaFarmaci lista di farmaci di cui aggiornare la quantita
     * @param id_farmacia id della farmacia che fa la richiesta
     */
    public static boolean queryQuantitaFarmaci(Farmaco[] listaFarmaci, int id_farmacia){
        connectFarmacia();
        String query = "SELECT Lotto.id_farmaco, SUM(Lotto.quantita) FROM DB_Farmacie.Lotto WHERE Lotto.id_farmacia= ? GROUP BY Lotto.id_farmaco" ;
        try(PreparedStatement stmt = connFarmacia.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            HashMap<Integer, Farmaco> listaTemp = new HashMap<>();
            for(Farmaco farmaco : listaFarmaci){
                listaTemp.put(farmaco.getId_farmaco(), farmaco);
            }
            while(r.next()){
                int id_farmaco = r.getInt(1);
                int qta = r.getInt(2);
                if(listaTemp.containsKey(id_farmaco))
                    listaTemp.get(id_farmaco).riempiQuantita(qta);
            } return true;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
            return false;
        }
    }


    public static void testQuery() {
        connectFarmacia(); // provo a riconnettermi ad ogni query, metti caso che parte la connessione per qualche motivo
        var query = "SELECT Farmaco.* FROM DB_Farmacie.Farmaco WHERE Farmaco.nome = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, "Oki");
            var r = stmt.executeQuery();
            while (r.next()) {
                System.out.println("Cercato Oki, trovato  " + r.getInt(1));
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }


}
