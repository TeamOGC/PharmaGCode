package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.Farmacista;
import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.Main;

import java.sql.*;
import java.util.HashMap;


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
     * Inserisci una nuova entry nella tabella Farmacista
     *
     * @param id_farmacia
     * @param nome
     * @param cognome
     * @param email
     * @param password
     * @return true if success, false if error
     */
    public static boolean registraUtente(int id_farmacia, String nome, String cognome, String email, String password){
        connectFarmacia();
        String query= "INSERT INTO Farmacista (id_farmacia, nome, cognome, email,password) VALUES (?,?,?,?,?)";
        try(PreparedStatement stmt = connFarmacia.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            stmt.setString(2, nome);
            stmt.setString(3, cognome);
            stmt.setString(4, email);
            stmt.setString(5, password);
            var r= stmt.executeQuery();
            return true;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
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
     * Aggiorna la password di un utente conoscendo quella vecchia
     *
     * @param mail mail dell'utente
     * @param password password dell'utente
     * @param vecchiaPsw psw da modificare
     *
     * @return 1 if password aggiornata, -1 altrimenti
     */
    public static int modificaPassword(String mail, String password, String vecchiaPsw){
        connectFarmacia();
        String query= "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ? AND password=?";
        try(PreparedStatement stmt= connFarmacia.prepareStatement(query)){
            stmt.setString(1, password);
            stmt.setString(1, mail);
            stmt.setString(3, vecchiaPsw);
            var r= stmt.executeUpdate();
            if (r!=0)
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     *
     * @param mail mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     */
    public static int aggiornaPassword(String mail, String new_pwd){
        connectFarmacia();
        String query= "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ?";
        try(PreparedStatement stmt= connFarmacia.prepareStatement(query)){
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
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
     * @param nome_farmaco
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
    public static int modificaProduzione(int id_farmaco, int quantita){
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
     * @return ResultSet con tutti i dati degli ordini periodici dell'azienda
     */
    public static ResultSet queryOrdiniPeriodici(){
        connectAzienda();
        String query = "SELECT OrdinePeriodico.* FROM OrdinePeriodico";
        try(PreparedStatement stmt =connAzienda.prepareStatement(query)){
            var r =stmt.executeQuery();
            return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Query per chiedere tutti gli ordini con stato in attesa da parte dell'azienda
     *
     * @return tutti gli ordini in attesa
     */
    public static ResultSet queryOrdiniInAttesa(){
        connectAzienda();
        String query ="SELECT Ordine.* FROM Ordine WHERE LOWER(Ordine.stato)='in attesa'";
        try(PreparedStatement stmt =connAzienda.prepareStatement(query)){
            var r = stmt.executeQuery();
            return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * aggiorna la quantità ordinabile (?)
     *
     * @param id_ordine
     * @param qty_ordinabile
     * @return
     */
    public static int aggiornaQuantitaOrdine(int id_ordine, int qty_ordinabile){
        connectAzienda();
        String query= "UPDATE Ordine SET Ordine.quantita=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
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
    public static ResultSet visualizzaOrdiniAzienda(){
        connectAzienda();
        String query= "SELECT Ordine.* FROM Ordine";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            var r = stmt.executeQuery();
            if (r.next())
                return r;
        } catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per creare un ordine
     *
     * @param ordine ordine da creare
     * @return true if ordine creato correttamente, false if error
     */
    public static boolean queryCreaOrdine(Ordine ordine){
        connectAzienda();
        String query="INSERT INTO Ordine(id_farmacia, id_farmaco, data_consegna, stato) VALUES (?,?,?,?)";
        try(PreparedStatement stmt= connAzienda.prepareStatement(query)){
            stmt.setInt(1,ordine.getId_farmacia());
            stmt.setInt(2,ordine.getId_farmaco());
            stmt.setDate(3,ordine.getData_consegna());
            stmt.setString(4,ordine.getStato());

            int r=stmt.executeUpdate();
            if(r!=0)
                return true;
        }catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
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
            stmt.setDate(6, ordine.getData_consegna());
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
     * @param id_farmacia id della farmacia che fa la richiesta
     * @return resultSet contenente gli ordini effettuati ( le colonne corrispondono alle colonne della tabella ordine)
     */
    public static ResultSet visualizzaOrdiniFarmacia(int id_farmacia){
        connectAzienda();
        String query = "SELECT Ordine.* FROM Ordine WHERE Ordine.id_farmacia=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            if (r.next())
                return r;
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
    public static int aggiornaQuantitaOrdine(Ordine ordine, int nuova_qty){
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

    /**
     * query che permette di modificare la data di consegna di un ordine da parte di un farmacista
     *
     * @param ordine ordine di cui  modificare la data di consegna
     * @param nuova_data nuova data di consegna scelta dal farmacista
     * @return 1 if success, -1 if error
     */
    public static int aggiornaDataConsegnaOrdine(Ordine ordine, Date nuova_data){
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.data_consegna=? WHERE Ordine.id_ordine=?";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
            stmt.setDate(1, nuova_data);
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
     * @return ResultSet con gli ordini periodici della farmacia (Le colonne sono le colonne della tabella OrdinePeriodico)
     */
    public static ResultSet queryOrdiniPeriodici(int id_farmacia){
        connectAzienda();
        String query= "SELECT OrdinePeriodico.* FROM OrdinePeriodico WHERE OrdinePeriodico.id_farmacia=?";
        try(PreparedStatement stmt=connAzienda.prepareStatement(query)){
            stmt.setInt(1, id_farmacia);
            var r= stmt.executeQuery();
            return r;
        } catch(SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    public static void cercaFarmaco(){
        //TODO
    }

    /**
     * query per modificare la quantità di un ordine periodico da parte di un farmacista
     *
     * @param ordine ordine periodico da modificare
     * @param nuova_qty nuova quantità da settare
     * @return 1 if success, -1 if error
     */
    public static int modificaOrdinePeriodico(Ordine ordine, int nuova_qty){
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

    public static boolean firmaCollo(String firma){
        connectAzienda();
        String query = "UPDATE Ordine O SET O.firma = ? WHERE O.id_ordine IN (SELECT C.id_ordine FROM Collo C Where C.data_consegna = ? AND C.id_farmacia = ?)";
        try(PreparedStatement stmt = connAzienda.prepareStatement(query)){
        // TODO finire metodo non so come passare il collo che è una vista (creare entity collo?)
        }catch (SQLException e){
            erroreComunicazioneDBMS(e);
        }
        return false;
    }




}
