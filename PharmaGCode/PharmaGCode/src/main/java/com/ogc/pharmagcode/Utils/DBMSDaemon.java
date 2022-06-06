package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.*;
import com.ogc.pharmagcode.Main;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings({"unused", "UnusedReturnValue", "DuplicatedCode"})
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


    /**
     * Query per controllare le credenziali di un utente in base alla variabile {@code sistema} di {@link Main}
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryControllaCredenzialiFarmacista(String, String) ControllaCredenzialiFarmacista()}<br>
     * Se {@code Main.sistema == 1} allora {@link #queryControllaCredenzialiImpiegato(String, String) ControllaCredenzialiImpiegato()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryControllaCredenzialiCorriere(String, String) ControllaCredenzialiCorriere()}<br>
     *
     *
     * @param mail mail da controllare
     * @param password password da controllare
     *
     * @return Oggetto di tipo {@link Utente}. Fai il cast a {@link Farmacista}, {@link Impiegato} o {@link Corriere} a seconda del sistema in cui ti trovi
     */
    public static Utente queryControllaCredenziali(String mail, String password){
        if(Main.sistema == 0) return queryControllaCredenzialiFarmacista(mail, password);
        if(Main.sistema == 1) return queryControllaCredenzialiCorriere(mail, password);
        if(Main.sistema == 2) return queryControllaCredenzialiImpiegato(mail, password);
        return null;//non dovrebbe succedere mai?
    }

    /**
     * Query per registare un utente in base alla variabile {@code sistema} di {@link Main}.
     *<br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryRegistraFarmacista(int, String, String, String, String) queryRegistraFarmacista(Main.idFarmacia)} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryRegistraCorriere(String, String, String, String) queryRegistraCorriere()}<br>
     * Se {@code Main.sistema == 2} allora {@link #queryRegistraImpiegato(String, String, String, String) queryRegistraImpiegato()}<br>
     * @param nome nome utente
     * @param cognome cognome utente
     * @param email email utente
     * @param password password utente hashata
     * @return {@code true} se la registrazione ha avuto successo, {@code false} se non ha avuto successo
     */
    public static boolean queryRegistraUtente(String nome, String cognome, String email, String password){
        if(Main.sistema==0) return queryRegistraFarmacista(Main.idFarmacia, nome, cognome, email, password);
        if(Main.sistema==1) return queryRegistraImpiegato(nome, cognome, email, password);
        if(Main.sistema==2) return queryRegistraCorriere(nome, cognome, email, password);
        return false;
    }

    /**
     * Query per verificare 'esistenza di una mail in base alla variabile {@code sistema} di {@link Main}.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryVerificaEsistenzaMailFarmacista(String) queryVerificaEsistenzaMailFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryVerificaEsistenzaMailCorriere(String) queryVerificaEsistenzaMailCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryVerificaEsistenzaMailImpiegato(String) queryVerificaEsistenzaMailImpiegato()} <br>
     * @param mail mail da verificare
     * @return true o false se esiste o meno
     */
    public static boolean queryVerificaEsistenzaMail(String mail){
        if(Main.sistema==0) return queryVerificaEsistenzaMailFarmacista(mail);
        if(Main.sistema==1) return queryVerificaEsistenzaMailImpiegato(mail);
        if(Main.sistema==2) return queryVerificaEsistenzaMailCorriere(mail);
        return false;
    }

    /**
     * Query per modificare la password di un utente conoscendo quella vecchia in base alla variabile {@code sistema} di {@link Main}.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryModificaPasswordFarmacista(String, String, String) queryModificaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryModificaPasswordCorriere(String, String, String) queryModificaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryModificaPasswordImpiegato(String, String, String) queryModificaPasswordImpiegato()} <br>
     * @param mail mail dell'utente
     * @param password nuova password
     * @param vecchiaPassword vecchia password
     *
     * @return True se la password è stata modificata con successo
     */
    public static boolean queryModificaPassword(String mail, String password, String vecchiaPassword){
        if(Main.sistema == 0) return queryModificaPasswordFarmacista(mail, password, vecchiaPassword);
        if(Main.sistema == 1) return queryModificaPasswordCorriere(mail, password, vecchiaPassword);
        if(Main.sistema == 2) return queryModificaPasswordImpiegato(mail, password, vecchiaPassword);
        return false;//non dovrebbe succedere mai?
    }

    /**
     * Aggiorna la password di {@code mail} senza sapere la password vecchia, usato nel caso d'uso recupera credenziali.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryAggiornaPasswordFarmacista(String, String) queryAggiornaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryAggiornaPasswordCorriere(String, String) queryAggiornaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryAggiornaPasswordImpiegato(String, String) queryAggiornaPasswordImpiegato()} <br>
     * @param mail mail dell'account
     * @param new_pwd nuova password generata dal sistema
     *
     * @return True se la password è stata aggiornata con successo
     */
    public static boolean queryAggiornaPassword(String mail, String new_pwd){
        if(Main.sistema == 0) return queryAggiornaPasswordFarmacista(mail, new_pwd);
        if(Main.sistema == 1) return queryAggiornaPasswordCorriere(mail, new_pwd);
        if(Main.sistema == 2) return queryAggiornaPasswordImpiegato(mail, new_pwd);
        return false; // non dovrebbe mai succedere
    }


// -- Query Gestione Account

    /**
     * Controlla Credenziali utenza farmacista
     *
     * @param mail Mail del farmacista
     * @param password Password del farmacista
     * @return {@link Farmacista}
     */
    private static Farmacista queryControllaCredenzialiFarmacista(String mail, String password) {
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
     * Controlla Credenziali utenza impiegato
     *
     * @param mail mail dell'impiegato
     * @param password password dell'impiegato
     * @return {@link Impiegato}
     */
    private static Impiegato queryControllaCredenzialiImpiegato(String mail, String password) {
        connectAzienda();
        var query = "SELECT Impiegato.* FROM DB_Azienda.Impiegato WHERE email = ? and password = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Impiegato.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Controlla Credenziali utenza corriere
     *
     * @param mail mail del corriere
     * @param password password del corriere
     * @return {@link Corriere}
     */
    private static Corriere queryControllaCredenzialiCorriere(String mail, String password) {
        connectAzienda();
        var query = "SELECT Corriere.* FROM DB_Azienda.Corriere WHERE email = ? and password = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, mail);
            stmt.setString(2, password);
            var r = stmt.executeQuery();
            if (r.next()) {
                return Corriere.createFromDB(r);
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }


    /**
     * Aggiorna la password di un Farmacista quella vecchia
     *
     * @param mail mail dell'utente
     * @param password password dell'utente
     * @param vecchiaPsw psw da modificare
     *
     * @return true se la password è stata aggiornata
     */
    private static boolean queryModificaPasswordFarmacista(String mail, String password, String vecchiaPsw){
        connectFarmacia();
        String query= "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ? AND password=?";
        try(PreparedStatement stmt= connFarmacia.prepareStatement(query)){
            stmt.setString(1, password);
            stmt.setString(1, mail);
            stmt.setString(3, vecchiaPsw);
            var r= stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Aggiorna la password di un Impiegato conoscendo quella vecchia
     *
     * @param mail mail dell'utente
     * @param password password dell'utente
     * @param vecchiaPsw psw da modificare
     *
     * @return 1 if password aggiornata, -1 altrimenti
     */
    private static boolean queryModificaPasswordImpiegato(String mail, String password, String vecchiaPsw){
        connectAzienda();
        String query= "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ? AND password=?";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setString(1, password);
            stmt.setString(1, mail);
            stmt.setString(3, vecchiaPsw);
            var r= stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Aggiorna la password di un Corriere conoscendo quella vecchia
     *
     * @param mail mail dell'utente
     * @param password password dell'utente
     * @param vecchiaPsw psw da modificare
     *
     * @return 1 if password aggiornata, -1 altrimenti
     */
    private static boolean queryModificaPasswordCorriere(String mail, String password, String vecchiaPsw){
        connectAzienda();
        String query= "UPDATE Corriere SET Corriere.password = ? WHERE email = ? AND password=?";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setString(1, password);
            stmt.setString(1, mail);
            stmt.setString(3, vecchiaPsw);
            var r= stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    private static boolean queryAggiornaPasswordCorriere(String mail, String new_pwd){
        connectAzienda();
        String query= "UPDATE Corriere SET Corriere.password = ? WHERE email = ?";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r= stmt.executeUpdate();

            return r==1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     *
     * @param mail mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     */
    private static boolean queryAggiornaPasswordFarmacista(String mail, String new_pwd){
        connectFarmacia();
        String query= "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ?";
        try(PreparedStatement stmt= connFarmacia.prepareStatement(query)){
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r= stmt.executeUpdate();

            return r==1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     *
     * @param mail mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     */
    private static boolean queryAggiornaPasswordImpiegato(String mail, String new_pwd){
        connectAzienda();
        String query= "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ?";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r= stmt.executeUpdate();

            return r==1;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }
    

    /**
     * Inserisci una nuova entry nella tabella Farmacista
     *
     * @param id_farmacia id farmacia
     * @param nome nome farmacista
     * @param cognome cognome farmacista
     * @param email mail farmacista
     * @param password password hashata
     * @return true if success, false if error
     */
    public static boolean queryRegistraFarmacista(int id_farmacia, String nome, String cognome, String email, String password){
        connectFarmacia();
        String query= "INSERT INTO Farmacista (id_farmacia, nome, cognome, email,password) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = connFarmacia.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            stmt.setString(2, nome);
            stmt.setString(3, cognome);
            stmt.setString(4, email);
            stmt.setString(5, password);
            return stmt.executeUpdate()>0;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * query per registrare un impiegato nel db
     *
     * @param nome nome impiegato
     * @param cognome cognome impiegato
     * @param email mail impiegato
     * @param password password hashata
     * @return true if success, false if error
     */
    public static boolean queryRegistraImpiegato(String nome, String cognome, String email, String password){
        connectAzienda();
        String query= "INSERT INTO Impiegato (nome, cognome, email,password) VALUES (?,?,?,?)";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * query per registrare un corriere all'interno del db
     *
     * @param nome nome corriere
     * @param cognome cognome corriere
     * @param email mail corriere
     * @param password password hashata
     * @return true o false se la registrazione va a buon fine o meno
     */
    public static boolean queryRegistraCorriere(String nome, String cognome, String email, String password){
        connectAzienda();
        String query= "INSERT INTO Corriere (nome, cognome, email,password) VALUES (?,?,?,?)";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }



    /**
     *  Verifica l'esistenza della mail di un farmacista all'interno del db
     *
     * @param mail email del farmacista
     * @return true if email in DB, false if not
     *
     */
    public static boolean queryVerificaEsistenzaMailFarmacista(String mail){
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
     * query per verificare l'esistenza della mail di un impiegato nel db in fase di registrazione e di recupero credenziali
     *
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     */
    public static boolean queryVerificaEsistenzaMailImpiegato(String mail){
        connectAzienda();
        String query= "SELECT Impiegato.email FROM Impiegato WHERE email = ?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
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
     * query per verificare l'esistenza della mail di un corriere nel db in fase di registrazione e di recupero credenziali
     *
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     */
    public static boolean queryVerificaEsistenzaMailCorriere(String mail){
        connectFarmacia();
        String query= "SELECT Corriere.email FROM Corriere WHERE email = ?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
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

// -- Query Farmacia


    /**
     * Controlla scorte farmacia dopo lo scarico merci
     *
     * @param id_lotto lotto del farmaco scaricato
     * @param id_farmacia farmacia che ha scaricato il farmaco
     * @return quantità ancora disponibili dalla farmacia
     */
    public static int queryControlloScorte(int id_lotto, int id_farmacia) {
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
    public static void queryCaricaFarmaco(int id_lotto, int id_farmacia, LocalDate data_caricamento, int qty) {
        connectFarmacia();
        try (CallableStatement call = connFarmacia.prepareCall("{CALL caricoMerci(?, ?, ?, ?, ?)}")) {
            call.setInt(1, id_lotto);
            call.setInt(2, id_farmacia);
            call.setDate(3, Date.valueOf(data_caricamento));
            call.setInt(4, qty);
            call.execute();

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Consente di aggiornare la quantità consegnata dell'ordine appena caricato
     * @param id_ordine id dell'ordine appena caricato
     * @param id_lotto id del lotto appena caricato
     * @param qty quantità caricata
     * @return true se aggiorna almeno un record
     */
    public static boolean aggiornaQuantitaConsegnataOrdine(int id_ordine, int id_lotto, int qty){
        connectAzienda();
        String query="UPDATE DB_Azienda.ComposizioneOrdine SET ComposizioneOrdine.quantita_consegnata=ComposizioneOrdine.quantita_consegnata + ? WHERE ComposizioneOrdine.id_ordine=? AND ComposizioneOrdine.id_lotto = ?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1,qty);
            stmt.setInt(2,id_ordine);
            stmt.setInt(3,id_lotto);
            return stmt.executeUpdate() > 0;
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Consente di effettuare lo scarico di un farmaco appena venduto
     *
     * @param id_lotto id del lotto che viene scaricato
     * @param id_farmacia id della farmacia che effettua lo scarico
     * @param qty quantità di farmaco che viene scaricato
     * @return quantità di farmaco ancora disponibile in quella farmacia, -1 se ha errorato
     */
    public static int queryScaricaMerci(int id_lotto, int id_farmacia, int qty) {
        connectFarmacia();
        String query = "UPDATE DB_Farmacie.Lotto SET Lotto.quantita=((SELECT Lotto.quantita FROM DB_Farmacie.Lotto WHERE Lotto.id_lotto=?)-?) WHERE Lotto.id_lotto=? AND Lotto.id_farmacia=?";
        try (PreparedStatement stmt= connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, id_lotto);
            stmt.setInt(2, qty);
            stmt.setInt(3, id_lotto);
            stmt.setInt(4, id_farmacia);
            stmt.executeUpdate();
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
     * @param data_caricamento {@link LocalDate} data in cui parte la richiesta
     * @return {@link HashMap} che contiene come chiave {@code id_lotto} ({@link Integer}) e come valore la rispettiva {@code quantita} ({@link Integer}) caricata
     */
    public static HashMap<Integer, Integer> queryMerceCaricata(int id_farmacia, LocalDate data_caricamento) {
        connectFarmacia();
        HashMap<Integer, Integer> foo = new HashMap<>();
        String query = "SELECT Caricamenti.id_lotto, Caricamenti.quantita FROM Caricamenti WHERE Caricamenti.data_caricamento=? AND Caricamenti.id_farmacia=?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(data_caricamento));
            stmt.setInt(2, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()){
                foo.put(r.getInt(1), r.getInt(2));
            }
            return foo;
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
        connectAzienda();
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

    //Query DB Azienda

    /**
     * Controlla se un farmaco esiste nel db per poterne modificare la produzione
     *
     * @param nome_farmaco nome del farmaco
     * @return true if exists, false if not
     */
    public static boolean queryControlloEsistenzaFarmaco(String nome_farmaco){
        connectAzienda();
        String query = "SELECT ProduzionePeriodica.id_farmaco FROM ProduzionePeriodica INNER JOIN Farmaco F on ProduzionePeriodica.id_farmaco = F.id_farmaco WHERE F.nome=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setString(1, nome_farmaco);
            var r=stmt.executeQuery();
            if (r.next())
                return true;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        } return false;
    }

    /**
     * Modifica la produzione di un farmaco in Azienda
     *
     * @param id_farmaco id del farmaco di cui modificare la produzione
     * @param quantita nuova quantita da impostare
     * @return 1 if success, -1 if not
     */
    public static int queryModificaProduzione(int id_farmaco, int quantita){
        connectAzienda();
        String query= "UPDATE ProduzionePeriodica SET ProduzionePeriodica.quantita=? WHERE ProduzionePeriodica.id_farmaco=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setInt(1, quantita);
            stmt.setInt(2, id_farmaco);
            var r=stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Query per chiedere tutti gli ordini periodici
     *
     * @return {@link OrdinePeriodico}[] contenente tutti gli ordini periodici.
     * Ritorna null se ci sono stati errori o non sono stati trovati risultati.
     * con tutti i dati degli ordini peri´dici dell'azienda
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(){
        connectAzienda();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia";
        ArrayList<OrdinePeriodico> ordini = new ArrayList<>();
        try(PreparedStatement stmt =connAzienda.prepareStatement(query)){
            var r =stmt.executeQuery();
            while(r.next()){
                ordini.add(OrdinePeriodico.createFromDB(r));
            }
            return ordini.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Query per chiedere tutti gli ordini con stato in attesa da parte dell'azienda
     *
     * @return {@link Ordine}[] tutti gli ordini in attesa. {@code null} se non
     * sono stati trovati risultati o erroreComunicazioneDBMS
     */
    public static Ordine[] queryOrdiniInAttesa(){
        connectAzienda();
        String query ="SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE LOWER(Ordine.stato)='in attesa'";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try(PreparedStatement stmt =connAzienda.prepareStatement(query)){
            var r = stmt.executeQuery();
            while(r.next()){
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * aggiorna la quantità ordinabile (?)
     *
     * @param ordine {@link Ordine} ordine di cui modificare la quantita
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaQuantitaOrdine(Ordine ordine){
        connectAzienda();
        String query= "UPDATE Ordine SET Ordine.quantita=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1, ordine.getQuantita());
            stmt.setInt(2,ordine.getId_ordine());
            var r=stmt.executeUpdate();
            if (r !=0)
                return r;
        }
        catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query per aggiornare lo stato di un ordine
     *
     * @param ordine ordine da aggiornare
     * @return 1 if update success, -1 if update failed
     */
    public static int queryAggiornaStatoOrdine(Ordine ordine){
        connectAzienda();
        String query= "UPDATE Ordine SET Ordine.stato=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, ordine.getStato());
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query per visualizzare gli ordini lato azienda
     *
     * @return ResultSet contenente tutti gli ordini lato azienda (Le colonne corrispondono alle colonne della tabella ordine del DB)
     */
    public static Ordine[] queryVisualizzaOrdiniAzienda(){
        connectAzienda();
        String query= "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco ORDER BY Ordine.data_consegna DESC";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while(r.next()){
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per creare un ordine
     *
     * @param ordine {@link Ordine} da creare
     * @return true if ordine creato correttamente, false if error
     */
    public static boolean queryCreaOrdine(Ordine ordine){

        connectAzienda();
        String query="INSERT INTO Ordine(id_farmacia, id_farmaco, data_consegna, stato, quantita) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setInt(1,ordine.getId_farmacia());
            stmt.setInt(2,ordine.getId_farmaco());
            stmt.setDate(3, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(4,ordine.getStato());
            stmt.setInt(5, ordine.getQuantita());
            int r=stmt.executeUpdate();
            if(r!=0)
                return true;
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    private static Lotto[] queryLotti(int id_farmaco, boolean accettaInScadenza){
        connectAzienda();
        String query="SELECT Lotto.* FROM Lotto WHERE Lotto.id_farmaco=? AND Lotto.data_scadenza>? ORDER BY Lotto.data_scadenza ASC";
        LocalDate d=Main.orologio.chiediOrario().toLocalDate();
        ArrayList<Lotto> lotti=new ArrayList<>();
        if(!accettaInScadenza){
            d = d.plusMonths(2);
        }
        Date data=Date.valueOf(d);
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1,id_farmaco);
            stmt.setDate(2,data);
            ResultSet r=stmt.executeQuery();
            while(r.next()){
                lotti.add(new Lotto(r.getInt(2),r.getInt(1),r.getInt(4)));
            }
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return lotti.toArray(new Lotto[0]);
    }

    private static int queryScegliLotti(Lotto[] lotti, int quantita, ArrayList<Lotto> composizione){
        connectAzienda();
        String query="UPDATE Lotto SET quantita=quantita-? WHERE Lotto.id_lotto=?";
        ArrayList<Lotto> temp=new ArrayList<>();
        for(Lotto l:lotti){
            if (quantita > l.getQuantita()){
                quantita-=l.getQuantita();
                temp.add(new Lotto(l));
                l.setQuantita(0);
            }else{
                Lotto l1=new Lotto(l);
                l1.setQuantita(quantita);
                temp.add(l1);
                l.setQuantita(l.getQuantita()-quantita);
                quantita=0;
            }
        }
        if(quantita>0){
            return quantita;
        }
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            for(Lotto l : temp){
                stmt.setInt(1,l.getQuantita());
                stmt.setInt(2,l.getId_lotto());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return 0;
        }catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    private static int creaComposizioneOrdini(int id_farmaco, int quantita, boolean accettaScadenza, ArrayList<Lotto> composizione){
        int quantitaRimanente=queryScegliLotti(queryLotti(id_farmaco,accettaScadenza), quantita, composizione);
        return quantitaRimanente;
    }

    private static int queryCreaComposizioneOrdini(int id_farmaco, int quantita, boolean accettaScadenza, int id_ordine){
        String query="INSERT INTO ComposizioneOrdine(id_ordine,id_lotto,quantita) VALUES (?,?,?)";
        ArrayList<Lotto> composizione=new ArrayList<>();
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            int quantitaRimanente=creaComposizioneOrdini(id_farmaco, quantita, accettaScadenza, composizione);
            if(quantitaRimanente>0)
                return quantitaRimanente;
            for(Lotto l:composizione){
                stmt.setInt(1,id_ordine);
                stmt.setInt(2,l.getId_lotto());
                stmt.setInt(3,l.getQuantita());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
            return -1;
        }
        return 0;
    }

    private static int queryQuantitaFarmaco(int id_farmaco){
        connectAzienda();
        String query="SELECT id_farmaco, sum(quantita) FROM Lotto WHERE id_farmaco=? GROUP BY id_farmaco";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1,id_farmaco);
            ResultSet r=stmt.executeQuery();
            if(r.next()){
                return r.getInt(2);
            }
        }catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }
    public static int queryCreaOrdineTemp(Ordine ordine, boolean accettaScadenza){
        //Deve: chiedere i lotti, scegliere quali lotti verranno scelti
        // per quell'ordine, rimuovere i farmaci
        // e caricare ordine e composizione ordini
        //SE la quantità totale è inferiore alla quantità richiesta, ritorna false
        connectAzienda();
        String query="INSERT INTO Ordine(id_farmacia, id_farmaco, data_consegna, stato, quantita) VALUES (?,?,?,?,?)";

        try(PreparedStatement stmt= connAzienda.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
            connAzienda.setAutoCommit(false);
            stmt.setInt(1,ordine.getId_farmacia());
            stmt.setInt(2,ordine.getId_farmaco());
            stmt.setDate(3, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(4,ordine.getStato());
            stmt.setInt(5, ordine.getQuantita());
            int r=stmt.executeUpdate();
            if(ordine.getStato().equalsIgnoreCase("In Attesa di disponibilita")){
                connAzienda.commit();
                connAzienda.setAutoCommit(true);
                return 0;
            }
            ResultSet id_ordine=stmt.getGeneratedKeys();
            if(id_ordine.next()){
                int quantitaRimanente=queryCreaComposizioneOrdini(ordine.getId_farmaco(), ordine.getQuantita(), accettaScadenza, id_ordine.getInt(1));
                if(quantitaRimanente==0){
                    connAzienda.commit();
                    connAzienda.setAutoCommit(true);
                    return 0;
                }
                else{
                    connAzienda.rollback();
                    return quantitaRimanente;
                }
            }
            return -1;
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
            return -1;
        }
    }

    public static int queryCreaOrdine(Ordine ordine, String statoOrdine, boolean accettaInScadenza){
        ordine.setStato(statoOrdine);
        return queryCreaOrdineTemp(ordine,accettaInScadenza);
    }

    /**
     * query che consente a un impiegato di correggere un ordine
     *
     * @param qty_ricevuta quantità ricevuta dal farmacista
     * @param qty_da_integrare quantità che il farmacista vuole inviata per integrare
     * @param ordine ordine da correggere
     * @return true if success, false if error
     */
    public static boolean queryCorreggiOrdine(int qty_ricevuta, int qty_da_integrare, Ordine ordine){
        connectAzienda();
        String query="{CALL correggiOrdine(?, ?, ?, ?, ?, ?, ?)}";
        try(CallableStatement stmt= connAzienda.prepareCall(query)){
            stmt.setInt(1,qty_ricevuta);
            stmt.setInt(2,qty_da_integrare);
            stmt.setInt(3,ordine.getId_ordine());
            stmt.setInt(4,ordine.getId_farmaco());
            stmt.setInt(5,ordine.getId_farmacia());
            stmt.setDate(6, Date.valueOf(ordine.getData_consegna()));
            stmt.registerOutParameter(7, Types.TINYINT);
            var r=stmt.executeQuery();
            if(r.next())
                return r.getInt(7) == 1;
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Query che consente a una farmacia di visualizzare gli ordini effettuati
     *
     * @return {@link Ordine}[] contenente gli ordini effettuati.
     * {@code null} se non sono stati trovati risultati o errore
     * @param id_farmacia id della farmacia che fa la richiesta
     */
    public static Ordine[] queryVisualizzaOrdiniFarmacia(int id_farmacia){
        connectAzienda();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE Ordine.id_farmacia=?";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            while(r.next()){
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per modificare la quantità del farmaco di un ordine da parte di un farmacista
     *
     * @param ordine ordine di cui aggiornare la quantita
     * @param nuova_qty nuova quantita scelta dal farmacista
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaQuantitaOrdine(Ordine ordine, int nuova_qty){
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.quantita=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setInt(1, nuova_qty);
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    public static int queryAggiornaQuantitaOrdine(int idOrdine, int nuova_qty){
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.quantita=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setInt(1, nuova_qty);
            stmt.setInt(2, idOrdine);
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query che permette di modificare la data di consegna di un ordine da parte di un farmacista
     *
     * @param ordine ordine di cui  modificare la data di consegna
     * @param nuova_data nuova data di consegna scelta dal farmacista
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaDataConsegnaOrdine(Ordine ordine, LocalDate nuova_data){
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.data_consegna=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setDate(1, Date.valueOf(nuova_data));
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query per visualizzare tutti gli ordini periodici di una farmacia
     *
     * @param id_farmacia id della farmacia da cui parte la richiesta
     * @return {@link OrdinePeriodico}[]
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(int id_farmacia){
        connectAzienda();
        String query= "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia AND OrdinePeriodico.id_farmacia=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            var r= stmt.executeQuery();
            ArrayList<OrdinePeriodico> foo = new ArrayList<>();
            while(r.next()){
                foo.add(OrdinePeriodico.createFromDB(r));
            }
            return foo.toArray(new OrdinePeriodico[0]);
        } catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query che permette di cercare un farmaco. La ricerca può essere effettuata per nome, principio attivo o entrambi
     *
     * @param nome nome farmaco
     * @param principio_attivo principio attivo farmaco
     * @return {@link Farmaco}[] array di farmaci trovati, null se nessun risultato oppure erroreComunicazioneDBMS;
     */
    public static Farmaco[] querycercaFarmaco(String nome, String principio_attivo){
        connectAzienda();
        String query = "SELECT Farmaco.* FROM Farmaco WHERE LOWER(Farmaco.nome) LIKE ? AND LOWER(Farmaco.principio_attivo) LIKE ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setString(1, "%" + nome.toLowerCase() + "%");
            stmt.setString(2, "%" + principio_attivo.toLowerCase() + "%");
            var r = stmt.executeQuery();
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while(r.next()){
                farmaci.add(Farmaco.createFromDB(r));
            }
            return farmaci.toArray(new Farmaco[0]);
        }catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per modificare la quantità di un ordine periodico da parte di un farmacista
     *
     * @param ordine ordine periodico da modificare
     * @param nuova_qty nuova quantità da settare
     * @return 1 if success, -1 if error
     */
    public static int queryModificaOrdinePeriodico(Ordine ordine, int nuova_qty){
        connectAzienda();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, nuova_qty);
            stmt.setInt(2, ordine.getId_farmacia());
            stmt.setInt(3, ordine.getId_farmaco());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query per visualizzare le consegne giornaliere da parte del corriere
     *
     * @param data {@link LocalDate} data odierna
     *
     * @return {@link Collo}[]
     */
    public static Collo[] queryVisualizzaConsegne(LocalDate data){
        connectAzienda();
        HashMap<Integer, Collo> colli = new HashMap<>();
        String query = "SELECT O.*, F.nome FROM Ordine O INNER JOIN Farmaco F on O.id_farmaco = F.id_farmaco WHERE O.data_consegna = ?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setDate(1, Date.valueOf(data));
            var r= stmt.executeQuery();
            while(r.next()){
                Ordine ord = Ordine.createFromDB(r);
                if(!colli.containsKey(ord.getId_farmacia())){
                    Collo c = new Collo(ord.getId_farmacia(), data);
                    c.aggiungiOrdine(ord);
                    colli.put(ord.getId_farmacia(), c);
                }
                else{
                    colli.get(ord.getId_farmacia()).aggiungiOrdine(ord);
                }
            }
            return colli.values().toArray(new Collo[0]);
        }
        catch(SQLException e){erroreComunicazioneDBMS(e);}
        return null;
    }

    /**
     * query necessaria per firmare un collo
     *
     * @param firma concatenazione di nome e cognome del firmante
     * @param collo collo da firmare
     * @return true if success, false if error
     */
    public static boolean queryFirmaCollo(String firma, Collo collo){
        connectAzienda();
        String query = "UPDATE Ordine O SET O.firma = ? WHERE O.id_ordine IN (SELECT C.id_ordine FROM Collo C Where C.data_consegna = ? AND C.id_farmacia = ?)";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setString(1, firma);
            stmt.setDate(2, Date.valueOf(collo.getData_consegna()));
            stmt.setInt(3, collo.getId_farmacia());
            var res = stmt.executeUpdate();
            return res >= 1;
        }catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * query per aggiornare le scorte in un determinato giorno della settimana
     *
     * @param today {@link LocalDate}, serve per il giorno di produzione (1 LUN, ..., 7 DOM) e per capire quando scadrà il lotto prodotto
     */
    public static void queryAggiornaScorte(LocalDate today){
        connectAzienda();
        String query_get_produzioni = "SELECT P.* FROM ProduzionePeriodica P WHERE P.giorni_produzione = ?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query_get_produzioni)) {
            stmt.setInt(1, today.getDayOfWeek().getValue());
            var produzioni = stmt.executeQuery();
            String query_insert = "INSERT INTO Lotto (id_farmaco, data_scadenza, quantita) VALUES (?, ?, ?)";
            var updateStmt = connAzienda.prepareStatement(query_insert);
            while (produzioni.next()){
                int id_farmaco = produzioni.getInt(1);
                int quantita = produzioni.getInt(2);
                int scade_dopo_tot_giorni = produzioni.getInt(3);
                LocalDate scadenza = today.plusDays(scade_dopo_tot_giorni);
                updateStmt.setInt(1, id_farmaco);
                updateStmt.setDate(2, Date.valueOf(scadenza));
                updateStmt.setInt(3, quantita);
                updateStmt.addBatch();
            }
            updateStmt.executeBatch();
        }catch(SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    /**
     * query per creare un ordine periodico
     *
     * @param ordinePeriodico oggetto ordine periodico da aggiungere al db
     * @return 1 if success, -1 if error
     */
    public static int queryCreaOrdinePeriodico(OrdinePeriodico ordinePeriodico){
        connectAzienda();
        String query= "INSERT INTO OrdinePeriodico(id_farmacia, id_farmaco, quantita, periodicita) VALUES (?,?,?,?)";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setInt(1, ordinePeriodico.getId_farmacia());
            stmt.setInt(2, ordinePeriodico.getId_farmaco());
            stmt.setInt(3, ordinePeriodico.getQuantita());
            stmt.setInt(4, ordinePeriodico.getPeriodicita());
            var r= stmt.executeUpdate();
            if(r != 0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;

    }

    /**
     * query per aggiornare la quantita di un ordine periodico
     *
     * @param quantita nuova quantita
     * @param ordinePeriodico ordine periodico da modificare
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaOrdinePeriodico(int quantita, OrdinePeriodico ordinePeriodico){
        connectAzienda();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1, quantita);
            stmt.setInt(2,ordinePeriodico.getId_farmacia());
            stmt.setInt(3, ordinePeriodico.getId_farmaco());
            int r= stmt.executeUpdate();
            if(r!=0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * query per chiedere tutti gli ordini di una farmacia in una data
     *
     * @param id_farmacia id della farmacia che fa la richiesta
     * @param data data richiesta
     * @return {@link Ordine}[]
     */
    public static Ordine[] queryOrdiniDiUnaFarmaciaUnaData(int id_farmacia, LocalDate data){
        connectAzienda();
        String query="SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE Ordine.id_farmacia=? AND Ordine.data_consegna=?";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            stmt.setDate(2, Date.valueOf(data));
            var r=stmt.executeQuery();
            ArrayList<Ordine> foo = new ArrayList<>();
            while(r.next()){
                foo.add(Ordine.createFromDB(r));
            }
            return foo.toArray(new Ordine[0]);
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }
}
