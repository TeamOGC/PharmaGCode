package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.*;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import com.ogc.pharmagcode.Main;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings({"unused", "UnusedReturnValue", "DuplicatedCode", "SqlResolve"})
public class DBMSDaemon {
    /**
     * @hidden URL del Server DBMS
     */
    private static final String baseUrl = "beverlylab.duckdns.org";
    /**
     * @hidden Porta del Server DBMS
     */
    private static final int port = 11051;
    /**
     * @hidden Utente del server DBMS
     */
    private static final String user = "ogcadmin";
    /**
     * @hidden Password del server DBMS
     */
    private static final String pass = "OggiCarbonara";
    /**
     * @hidden Nome del DBMS Farmacie sul server
     */
    private static final String DBFarmacie = "DB_Farmacie";
    /**
     * @hidden Nome del DBMS Azienda sul server
     */
    private static final String DBAzienda = "DB_Azienda";
    /**
     * @hidden Connessione al DBMS Farmacie
     */
    private static Connection connFarmacia = null;
    /**
     * @hidden Connessione al DBMS Azienda
     */
    private static Connection connAzienda = null;


    /**
     * Metodo che mostra il pannello di errore e logga l'errore.
     *
     * @param e errore da loggare
     */
    private static void erroreComunicazioneDBMS(Exception e) {
        Main.log.error("Errore durante comunicazione con DBMS", e);
        Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");
    }

    /**
     * @hidden
     */
    private static String buildConnectionUrl(String dbName) {
        if (dbName.equals(DBFarmacie) || dbName.equals(DBAzienda))
            return "jdbc:mariadb://" + baseUrl + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
        return "";
    }

    /**
     * Metodo che permette di connettersi ad entrambi i Databases.
     * <p>
     * Vedi {@link DBMSDaemon#connectAzienda()} e {@link DBMSDaemon#connectFarmacia()}
     */
    public static void connect() {
        connectFarmacia();
        connectAzienda();
    }

    /**
     * Metodo utilizzato per connettersi al DBMS Farmacie.
     * Se la connessione è già stata stabilita in precedenza, viene utilizzata quest'ultima.
     */
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

    /**
     * Metodo utilizzato per connettersi al DBMS Azienda.
     * Se la connessione è già stata stabilita in precedenza, viene utilizzata quest'ultima.
     */
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
     * @param mail     mail da controllare
     * @param password password da controllare
     * @return Oggetto di tipo {@link Utente}. Fai il cast a {@link Farmacista}, {@link Impiegato} o {@link Corriere} a seconda del sistema in cui ti trovi
     */
    public static Utente queryControllaCredenziali(String mail, String password) {
        if (Main.sistema == 0) return queryControllaCredenzialiFarmacista(mail, password);
        if (Main.sistema == 1) return queryControllaCredenzialiCorriere(mail, password);
        if (Main.sistema == 2) return queryControllaCredenzialiImpiegato(mail, password);
        return null;//non dovrebbe succedere mai?
    }

    /**
     * Query per registare un utente in base alla variabile {@code sistema} di {@link Main}.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryRegistraFarmacista(int, String, String, String, String) queryRegistraFarmacista(Main.idFarmacia)} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryRegistraCorriere(String, String, String, String) queryRegistraCorriere()}<br>
     * Se {@code Main.sistema == 2} allora {@link #queryRegistraImpiegato(String, String, String, String) queryRegistraImpiegato()}<br>
     *
     * @param nome     nome utente
     * @param cognome  cognome utente
     * @param email    email utente
     * @param password password utente hashata
     * @return {@code true} se la registrazione ha avuto successo, {@code false} se non ha avuto successo
     */
    public static boolean queryRegistraUtente(String nome, String cognome, String email, String password) {
        if (Main.sistema == 0) return queryRegistraFarmacista(Main.idFarmacia, nome, cognome, email, password);
        if (Main.sistema == 1) return queryRegistraCorriere(nome, cognome, email, password);
        if (Main.sistema == 2) return queryRegistraImpiegato(nome, cognome, email, password);
        return false;
    }

    /**
     * Query per verificare 'esistenza di una mail in base alla variabile {@code sistema} di {@link Main}.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryVerificaEsistenzaMailFarmacista(String) queryVerificaEsistenzaMailFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryVerificaEsistenzaMailCorriere(String) queryVerificaEsistenzaMailCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryVerificaEsistenzaMailImpiegato(String) queryVerificaEsistenzaMailImpiegato()} <br>
     *
     * @param mail mail da verificare
     * @return true o false se esiste o meno
     */
    public static boolean queryVerificaEsistenzaMail(String mail) {
        if (Main.sistema == 0) return queryVerificaEsistenzaMailFarmacista(mail);
        if (Main.sistema == 1) return queryVerificaEsistenzaMailCorriere(mail);
        if (Main.sistema == 2) return queryVerificaEsistenzaMailImpiegato(mail);
        return false;
    }

    /**
     * Query per modificare la password di un utente conoscendo quella vecchia in base alla variabile {@code sistema} di {@link Main}.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryModificaPasswordFarmacista(String, String, String) queryModificaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryModificaPasswordCorriere(String, String, String) queryModificaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryModificaPasswordImpiegato(String, String, String) queryModificaPasswordImpiegato()} <br>
     *
     * @param mail            mail dell'utente
     * @param password        nuova password
     * @param vecchiaPassword vecchia password
     * @return True se la password è stata modificata con successo
     */
    public static boolean queryModificaPassword(String mail, String password, String vecchiaPassword) {
        if (Main.sistema == 0) return queryModificaPasswordFarmacista(mail, password, vecchiaPassword);
        if (Main.sistema == 1) return queryModificaPasswordCorriere(mail, password, vecchiaPassword);
        if (Main.sistema == 2) return queryModificaPasswordImpiegato(mail, password, vecchiaPassword);
        return false;//non dovrebbe succedere mai?
    }

    /**
     * Aggiorna la password di {@code mail} senza sapere la password vecchia, usato nel caso d'uso recupera credenziali.
     * <br><br>
     * Se {@code Main.sistema == 0} allora {@link #queryAggiornaPasswordFarmacista(String, String) queryAggiornaPasswordFarmacista()} <br>
     * Se {@code Main.sistema == 1} allora {@link #queryAggiornaPasswordCorriere(String, String) queryAggiornaPasswordCorriere()} <br>
     * Se {@code Main.sistema == 2} allora {@link #queryAggiornaPasswordImpiegato(String, String) queryAggiornaPasswordImpiegato()} <br>
     *
     * @param mail    mail dell'account
     * @param new_pwd nuova password generata dal sistema
     * @return True se la password è stata aggiornata con successo
     */
    public static boolean queryAggiornaPassword(String mail, String new_pwd) {
        if (Main.sistema == 0) return queryAggiornaPasswordFarmacista(mail, new_pwd);
        if (Main.sistema == 1) return queryAggiornaPasswordCorriere(mail, new_pwd);
        if (Main.sistema == 2) return queryAggiornaPasswordImpiegato(mail, new_pwd);
        return false; // non dovrebbe mai succedere
    }


// -- Query Gestione Account

    /**
     * @param mail     Mail del farmacista
     * @param password Password del farmacista
     * @return {@link Farmacista}
     * @hidden Controlla Credenziali utenza farmacista
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
     * @param mail     mail dell'impiegato
     * @param password password dell'impiegato
     * @return {@link Impiegato}
     * @hidden Controlla Credenziali utenza impiegato
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
     * @param mail     mail del corriere
     * @param password password del corriere
     * @return {@link Corriere}
     * @hidden Controlla Credenziali utenza corriere
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
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return true se la password è stata aggiornata
     * @hidden Aggiorna la password di un Farmacista quella vecchia
     */
    private static boolean queryModificaPasswordFarmacista(String mail, String password, String vecchiaPsw) {
        connectFarmacia();
        String query = "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return 1 if password aggiornata, -1 altrimenti
     * @hidden Aggiorna la password di un Impiegato conoscendo quella vecchia
     */
    private static boolean queryModificaPasswordImpiegato(String mail, String password, String vecchiaPsw) {
        connectAzienda();
        String query = "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail       mail dell'utente
     * @param password   password dell'utente
     * @param vecchiaPsw psw da modificare
     * @return 1 if password aggiornata, -1 altrimenti
     * @hidden Aggiorna la password di un Corriere conoscendo quella vecchia
     */
    private static boolean queryModificaPasswordCorriere(String mail, String password, String vecchiaPsw) {
        connectAzienda();
        String query = "UPDATE Corriere SET Corriere.password = ? WHERE email = ? AND password=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.setString(3, vecchiaPsw);
            var r = stmt.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordCorriere(String mail, String new_pwd) {
        connectAzienda();
        String query = "UPDATE Corriere SET Corriere.password = ? WHERE email = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordFarmacista(String mail, String new_pwd) {
        connectFarmacia();
        String query = "UPDATE Farmacista SET Farmacista.password = ? WHERE email = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail    mail dell'utente
     * @param new_pwd nuova password generata dal sistema
     * @return 1 if success, -1 if error
     * @hidden Query richiamata dentro Recupera credenziali, non necessita della vecchia psw per modificarla
     */
    private static boolean queryAggiornaPasswordImpiegato(String mail, String new_pwd) {
        connectAzienda();
        String query = "UPDATE Impiegato SET Impiegato.password = ? WHERE email = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, new_pwd);
            stmt.setString(2, mail);
            var r = stmt.executeUpdate();

            return r == 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }


    /**
     * @param id_farmacia id farmacia
     * @param nome        nome farmacista
     * @param cognome     cognome farmacista
     * @param email       mail farmacista
     * @param password    password hashata
     * @return true if success, false if error
     * @hidden Registra un nuovo farmacista all'interno del DB
     */
    private static boolean queryRegistraFarmacista(int id_farmacia, String nome, String cognome, String email, String password) {
        connectFarmacia();
        String query = "INSERT INTO Farmacista (id_farmacia, nome, cognome, email,password) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setString(2, nome);
            stmt.setString(3, cognome);
            stmt.setString(4, email);
            stmt.setString(5, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param nome     nome impiegato
     * @param cognome  cognome impiegato
     * @param email    mail impiegato
     * @param password password hashata
     * @return true if success, false if error
     * @hidden Registra un impiegato nel db
     */
    private static boolean queryRegistraImpiegato(String nome, String cognome, String email, String password) {
        connectAzienda();
        String query = "INSERT INTO Impiegato (nome, cognome, email,password) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param nome     nome corriere
     * @param cognome  cognome corriere
     * @param email    mail corriere
     * @param password password hashata
     * @return true o false se la registrazione va a buon fine o meno
     * @hidden Registra un corriere all'interno del db
     */
    private static boolean queryRegistraCorriere(String nome, String cognome, String email, String password) {
        connectAzienda();
        String query = "INSERT INTO Corriere (nome, cognome, email,password) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, email);
            stmt.setString(4, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }


    /**
     * @param mail email del farmacista
     * @return true if email in DB, false if not
     * @hidden Verifica l'esistenza della mail di un farmacista all'interno del db
     */
    public static boolean queryVerificaEsistenzaMailFarmacista(String mail) {
        connectFarmacia();
        String query = "SELECT Farmacista.email FROM Farmacista WHERE email = ?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     * @hidden Verifica l'esistenza l'esistenza della mail di un impiegato nel db in fase di registrazione e di recupero credenziali
     */
    private static boolean queryVerificaEsistenzaMailImpiegato(String mail) {
        connectAzienda();
        String query = "SELECT Impiegato.email FROM Impiegato WHERE email = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * @param mail mail da verificare
     * @return true if mail in db, false if not
     * @hidden Verifica l'esistenza della mail di un corriere nel db in fase di registrazione e di recupero credenziali
     */
    private static boolean queryVerificaEsistenzaMailCorriere(String mail) {
        connectAzienda();
        String query = "SELECT Corriere.email FROM Corriere WHERE email = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, mail);
            var r = stmt.executeQuery();
            if (r.next()) {
                Main.log.debug("Esiste un account Corriere con la mail " + mail + " - " + r.getString(1));
                return true;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        Main.log.debug("Non esiste un account Corriere con la mail " + mail);
        return false;
    }

// -- Query Farmacia


    /**
     * Consente di effettuare il caricamento merci, carica la merce nel DB Farmacie
     *  @param id_lotto         id del lotto
     * @param id_farmacia      id della farmacia
     * @param qty              quantita
     */
    public static void queryCaricaFarmaco(int id_lotto, int id_farmacia, int qty, int id_farmaco) {
        connectFarmacia();
        try (PreparedStatement call = connFarmacia.prepareStatement("INSERT INTO Lotto(quantita, id_lotto, id_farmacia, id_farmaco) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)");
                PreparedStatement stmt2=connFarmacia.prepareStatement("INSERT  INTO Caricamenti(quantita, id_lotto, id_farmacia, data_caricamento) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)")) {
            call.setInt(1, qty);
            stmt2.setInt(1,qty);
            call.setInt(2, id_lotto);
            stmt2.setInt(2, id_lotto);
            call.setInt(3, id_farmacia);
            stmt2.setInt(3, id_farmacia);
            call.setInt(4,id_farmaco);
            stmt2.setDate(4,Date.valueOf(Main.orologio.chiediOrario().toLocalDate()));
            call.executeUpdate();
            stmt2.executeUpdate();

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Carica la quantità consegnata nel DB Azienda
     *
     * @param id_ordine id dell'ordine appena caricato
     * @param id_lotto  id del lotto appena caricato
     * @param qty       quantità caricata
     * @return true se aggiorna almeno un record
     */
    public static boolean aggiornaQuantitaConsegnataOrdine(int id_ordine, int id_lotto, int qty) {
        connectAzienda();
        String query = "UPDATE DB_Azienda.ComposizioneOrdine SET ComposizioneOrdine.quantita_consegnata=ComposizioneOrdine.quantita_consegnata + ? WHERE ComposizioneOrdine.id_ordine=? AND ComposizioneOrdine.id_lotto = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, id_ordine);
            stmt.setInt(3, id_lotto);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Consente di effettuare lo scarico di un farmaco appena venduto
     * All'effettivo, decrementa la quantità del lotto in farmacia.
     * Ritorna la quantità rimasta in magazzino
     *
     * @param id_lotto    id del lotto che viene scaricato
     * @param id_farmacia id della farmacia che effettua lo scarico
     * @param qty         quantità di farmaco che viene scaricato
     * @return quantità di farmaco ancora disponibile in quella farmacia, -1 se ha errorato
     */
    public static int queryScaricaMerci(int id_lotto, int id_farmacia, int qty) {
        connectFarmacia();
        String query = "UPDATE DB_Farmacie.Lotto SET Lotto.quantita=Lotto.quantita-? WHERE Lotto.id_lotto=? AND Lotto.id_farmacia=?";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, qty);
            stmt.setInt(2, id_lotto);
            stmt.setInt(3, id_farmacia);
            if(stmt.executeUpdate()<=0)
                return -1;
            var squery = "SELECT Lotto.id_Farmaco,sum(quantita) FROM DB_Farmacie.Lotto WHERE Lotto.id_farmaco=(SELECT Lotto.id_farmaco FROM Lotto WHERE Lotto.id_lotto=? ) GROUP BY Lotto.id_farmaco";
            try (PreparedStatement sstmt = connFarmacia.prepareStatement(squery)) {
                sstmt.setInt(1, id_lotto);
                var risultato = sstmt.executeQuery();
                if (risultato.next()) {
                    return risultato.getInt(2);
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
     * @param id_farmacia      id della farmacia da cui parte la richiesta
     * @param data_caricamento {@link LocalDate} data in cui parte la richiesta
     * @return {@link HashMap} che contiene come chiave {@code id_ordine} ({@link Integer}) e come valore la rispettiva {@code quantita} ({@link Integer}) caricata
     */
    public static HashMap<Integer, Integer> queryMerceCaricata(int id_farmacia, LocalDate data_caricamento) {
        connectFarmacia();
        HashMap<Integer, Integer> foo = new HashMap<>();
        String query = "SELECT id_farmaco, sum(Caricamenti.quantita) FROM Caricamenti, Lotto WHERE Caricamenti.data_caricamento=? AND Caricamenti.id_farmacia=? AND Caricamenti.id_lotto=Lotto.id_lotto GROUP BY Lotto.id_farmaco";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(data_caricamento));
            stmt.setInt(2, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()) {
                foo.put(r.getInt(1), r.getInt(2));
            }
            return foo;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Per ogni farmaco viene recuperata la quantità presente nel magazzino della singola farmacia identificata da {@code id_farmacia}
     *
     * @param listaFarmaci lista di farmaci di cui aggiornare la quantità
     * @param id_farmacia  id della farmacia che fa la richiesta
     */
    public static boolean queryQuantitaFarmaci(Farmaco[] listaFarmaci, int id_farmacia) {
        connectFarmacia();
        String query = "SELECT Lotto.id_farmaco, SUM(Lotto.quantita) FROM DB_Farmacie.Lotto WHERE Lotto.id_farmacia= ? GROUP BY Lotto.id_farmaco";
        try (PreparedStatement stmt = connFarmacia.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            HashMap<Integer, Farmaco> listaTemp = new HashMap<>();
            for (Farmaco farmaco : listaFarmaci) {
                listaTemp.put(farmaco.getId_farmaco(), farmaco);
            }
            while (r.next()) {
                int id_farmaco = r.getInt(1);
                int qta = r.getInt(2);
                if (listaTemp.containsKey(id_farmaco))
                    listaTemp.get(id_farmaco).riempiQuantita(qta);
            }
            return true;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
    }

    //Query DB Azienda

    /**
     * Controlla se un farmaco esiste nel db per poterne modificare la produzione
     *
     * @param nome_farmaco nome del farmaco
     * @return id del farmaco corrispondente. -1 se non è stato trovato
     */
    public static int queryControlloEsistenzaFarmaco(String nome_farmaco) {
        connectAzienda();
        String query = "SELECT ProduzionePeriodica.id_farmaco FROM ProduzionePeriodica INNER JOIN Farmaco F on ProduzionePeriodica.id_farmaco = F.id_farmaco WHERE F.nome=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, nome_farmaco);
            var r = stmt.executeQuery();
            if (r.next())
                return r.getInt(1);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Modifica la produzione di un farmaco in Azienda
     *
     * @param id_farmaco id del farmaco di cui modificare la produzione
     * @param quantita   nuova quantita da impostare
     * @return true o false se con successo o meno
     */
    public static boolean queryModificaProduzione(int id_farmaco, int quantita) {
        connectAzienda();
        String query = "UPDATE ProduzionePeriodica SET ProduzionePeriodica.quantita=? WHERE ProduzionePeriodica.id_farmaco=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, quantita);
            stmt.setInt(2, id_farmaco);
            return stmt.executeUpdate() != 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }



    /**
     * Recupera tutti gli ordini con stato in attesa da parte dell'azienda
     *
     * @return {@link Ordine}[] tutti gli ordini in attesa. {@code null} se non
     * sono stati trovati risultati o erroreComunicazioneDBMS
     */
    public static Ordine[] queryOrdiniInAttesa() {
        connectAzienda();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE LOWER(Ordine.stato)='in attesa di disponibilita' order by Ordine.data_consegna";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Aggiorna la composizione degli ordini in seguito a una modifica della quantita dell'ordine
     *
     * @param ordine    ordine da aggiornare
     * @param nuova_qty nuova quantità dell'ordine
     */
    private static void queryAggiornaComposizioneOrdini(Ordine ordine, int nuova_qty) {
        int delta = nuova_qty - ordine.getQuantita();
        if (delta > 0) {
            queryCreaComposizioneOrdini(ordine.getId_farmaco(), delta, false, ordine);
            delta = 0;
            return;
        }

        String query = "SELECT ComposizioneOrdine.* FROM ComposizioneOrdine WHERE ComposizioneOrdine.id_ordine=?";
        String query2 = "UPDATE ComposizioneOrdine SET quantita=? WHERE id_ordine=? AND id_lotto=?";
        String query3 = "UPDATE Lotto SET quantita=quantita+? WHERE id_lotto=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query);
             PreparedStatement stmt2 = connAzienda.prepareStatement(query2);
             PreparedStatement stmt3 = connAzienda.prepareStatement(query3)) {
            stmt.setInt(1, ordine.getId_ordine());
            ResultSet r = stmt.executeQuery();
            stmt2.setInt(2, ordine.getId_ordine());
            if (delta < 0) {
                while (r.next()) {
                    stmt2.clearParameters();
                    stmt3.clearParameters();
                    stmt3.setInt(2, r.getInt("id_lotto"));
                    stmt2.setInt(2, ordine.getId_ordine());
                    stmt2.setInt(3,r.getInt("id_lotto"));
                    int qty = r.getInt("quantita");
                    Main.log.info("Nuova Iterazione");
                    /* Fa l'update del lotto*/
                    if (-delta >= qty) { // -delta=quantita da rimuovere, se è maggiore della quantita di lotto nell'ordine, rimuove totalmente il lotto dall'ordine e continua
                        stmt2.setInt(1, 0);
                        delta = delta + qty;
                        stmt2.addBatch();       //aggiunge alla batch la query di update della composizione
                        stmt3.setInt(1, qty);
                        stmt3.addBatch();       //aggiunge alla batch la query di update della quantita del lotto
                        Main.log.info("Delta dopo l'operazione: "+delta+" qty "+qty+" con -delta>=qty");
                    } else {   //altrimenti la quantita da rimuovere viene settata a 0 e può uscire dal loop
                        stmt2.setInt(1, qty + delta);
                        stmt2.addBatch();       //aggiunge alla batch la query di update della composizione
                        stmt3.setInt(1, -delta);
                        stmt3.addBatch();       //aggiunge alla batch la query di update della quantita del lotto
                        Main.log.info("Delta dopo l'operazione: "+delta+" qty "+qty+" con -delta<qty");
                        delta=0;
                        break;
                    }
                }
                stmt2.executeBatch();
                stmt3.executeBatch();
                //connAzienda.commit();
            }

        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
    }

    /**
     * Aggiorna la quantità di un ordine, se la quantità è settata a 0 elimina l'ordine
     *
     * @param ordine {@link Ordine} ordine di cui modificare la quantità
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaQuantitaOrdine(Ordine ordine, int nuova_qty) {
        connectAzienda();
        if (nuova_qty == 0) {
            String query = "DELETE FROM Ordine WHERE id_ordine=?";

            try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
                connAzienda.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, 0);
                stmt.setInt(1, ordine.getId_ordine());
                var r = stmt.executeUpdate();
                if (r != 0) {
                    connAzienda.commit();
                    connAzienda.setAutoCommit(true);
                    return r;
                } else {
                    connAzienda.rollback();
                    connAzienda.setAutoCommit(true);
                }
            } catch (SQLException e) {

                erroreComunicazioneDBMS(e);
            }
            return -1;
        } else {
            String query = "UPDATE Ordine SET Ordine.quantita=? WHERE Ordine.id_ordine=?";
            try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
                connAzienda.setAutoCommit(false);
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryAggiornaComposizioneOrdini(ordine, nuova_qty);
                stmt.setInt(1, nuova_qty);
                stmt.setInt(2, ordine.getId_ordine());
                var r = stmt.executeUpdate();
                if (r != 0) {
                    connAzienda.commit();
                    connAzienda.setAutoCommit(true);
                    return r;
                } else {
                    connAzienda.rollback();
                    connAzienda.setAutoCommit(true);
                }
            } catch (SQLException e) {
                erroreComunicazioneDBMS(e);
            }
            return -1;
        }
    }

    /**
     * Aggiornare lo stato di un ordine
     *
     * @param ordine ordine da aggiornare, con il nuovo stato
     * @return 1 if update success, -1 if update failed
     */
    public static int queryAggiornaStatoOrdine(Ordine ordine) {
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.stato=? WHERE Ordine.id_ordine=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, ordine.getStato());
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0) {
                if (ordine.getStato().equalsIgnoreCase("In Lavorazione"))
                    queryCreaComposizioneOrdini(ordine.getId_farmaco(), ordine.getQuantita(), false, ordine);
                return r;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini effettuati da tutte le farmacie. Utilizzato esclusivamente dall'Impiegato per effettuare
     * {@link com.ogc.pharmagcode.GestioneAzienda.Control.GestoreVisualizzaOrdiniAzienda VisualizzaOrdiniAzienda}
     *
     * @return {@link Ordine}[] lista contenente tutti gli ordini
     */
    public static Ordine[] queryVisualizzaOrdiniAzienda() {
        connectAzienda();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco ORDER BY Ordine.data_consegna DESC";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti i lotti di un relativo farmaco con una data di scadenza successiva alla {@code dataScadenza} (+2 mesi se {@code accettaInScadenza})
     *
     * @param id_farmaco        id del farmaco
     * @param accettaInScadenza se accetta farmaci in scadenza o meno
     * @param dataScadenza      data di scadenza
     * @return {@link Lotto}[] lista di lotti che soddisfano i requisiti
     */
    private static Lotto[] queryLotti(int id_farmaco, boolean accettaInScadenza, LocalDate dataScadenza) {
        connectAzienda();
        String query = "SELECT Lotto.* FROM Lotto WHERE Lotto.id_farmaco=? AND Lotto.data_scadenza>?  ORDER BY Lotto.data_scadenza";
        ArrayList<Lotto> lotti = new ArrayList<>();
        if (!accettaInScadenza) {
            dataScadenza = dataScadenza.plusMonths(2);
        }
        Date data = Date.valueOf(dataScadenza);
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmaco);
            stmt.setDate(2, data);
            ResultSet r = stmt.executeQuery();
            while (r.next()) {
                lotti.add(new Lotto(r.getInt(2), r.getInt(1), r.getInt(4)));
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        Main.log.debug("Trovati " + lotti.size() + " lotti");
        return lotti.toArray(new Lotto[0]);
    }

    // TODO: @Vincenzo scrivi qua la javadoc che non so che scrivere
    private static int queryScegliLotti(Lotto[] lotti, int quantita, ArrayList<Lotto> composizione) {
        connectAzienda();
        String query = "UPDATE Lotto SET quantita=quantita-? WHERE Lotto.id_lotto=?";
        ArrayList<Lotto> temp = new ArrayList<>();
        for (Lotto l : lotti) {
            if (l.getQuantita() == 0)
                continue;
            if (quantita > l.getQuantita()) {
                quantita -= l.getQuantita();
                temp.add(new Lotto(l));
                l.setQuantita(0);
            } else {
                Lotto l1 = new Lotto(l);
                l1.setQuantita(quantita);
                temp.add(l1);
                l.setQuantita(l.getQuantita() - quantita);
                quantita = 0;
                break;
            }
        }
        if (quantita > 0) {
            return quantita;
        }
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            for (Lotto l : temp) {
                stmt.setInt(1, l.getQuantita());
                stmt.setInt(2, l.getId_lotto());
                stmt.addBatch();
            }
            stmt.executeBatch();
            composizione.addAll(temp);
            return 0;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * @param id_farmaco
     * @param quantita
     * @param accettaScadenza
     * @param d
     * @param composizione
     * @return
     */
    private static int creaComposizioneOrdini(int id_farmaco, int quantita, boolean accettaScadenza, LocalDate d, ArrayList<Lotto> composizione) {
        return queryScegliLotti(queryLotti(id_farmaco, accettaScadenza, d), quantita, composizione);
    }

    /**
     * Transazione Atomica per comporre l'ordine dai lotti disponibili
     *
     * @param id_farmaco      id del farmaco
     * @param quantita        quantità da comporre
     * @param accettaScadenza se accetta farmaci in scadenza
     * @param ordine          ordine da comporre
     * @return Se l'operazione va a buon fine, ritorna {@code 0}.
     * Se non riesce a soddisfare l'ordine, ritorna un intero rappresentante la quantità rimanente che non può essere soddisfatta
     */
    private static int queryCreaComposizioneOrdini(int id_farmaco, int quantita, boolean accettaScadenza, Ordine ordine) {
        connectAzienda();
        String query = "INSERT INTO ComposizioneOrdine(id_ordine,id_lotto,quantita) VALUES (?,?,?) ON DUPLICATE KEY UPDATE quantita=quantita+VALUES(quantita)";

        ArrayList<Lotto> composizione = new ArrayList<>();
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            connAzienda.setAutoCommit(false);
            int quantitaRimanente = creaComposizioneOrdini(id_farmaco, quantita, accettaScadenza, ordine.getData_consegna(), composizione);
            Main.log.info("QuantitaRimanente nella query " + quantitaRimanente);
            if (quantitaRimanente > 0) {
                connAzienda.rollback();
                connAzienda.setAutoCommit(true);
                return quantitaRimanente;
            }
            for (Lotto l : composizione) {
                stmt.setInt(1, ordine.getId_ordine());
                stmt.setInt(2, l.getId_lotto());
                stmt.setInt(3, l.getQuantita());
                stmt.addBatch();
            }
            stmt.executeBatch();
            connAzienda.commit();
            connAzienda.setAutoCommit(true); //not sure
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return -1;
        }
        return 0;
    }

    /**
     * Recupera la quantità disponibile nel magazzino azienda, avendo l'identificativo del farmaco
     *
     * @param id_farmaco identificativo del farmaco
     * @return quantità presente nel magazzino dell'azienda, -1 se qualcosa va male
     */
    public static int queryQuantitaFarmaco(int id_farmaco) {
        connectAzienda();
        String query = "SELECT id_farmaco, sum(quantita) FROM Lotto WHERE id_farmaco=? GROUP BY id_farmaco";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmaco);
            ResultSet r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt("sum(quantita)");
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Prova a creare un ordine, se l'ordine è da contrassegnare in lavorazione e non sono disponibili le quantità richieste
     * l'ordine non viene effettuato e viene ritornata la quantita che non è stato possbile ordinare
     *
     * @param ordine          ordine da creare (o meglio, provarci)
     * @param accettaScadenza se si accettano farmaci in scadenza
     * @return Quantita eccedente
     */
    public static int queryCreaOrdine(Ordine ordine, boolean accettaScadenza) {

        //Deve: chiedere i lotti, scegliere quali lotti verranno scelti
        // per quell'ordine, rimuovere i farmaci
        // e caricare ordine e composizione ordini
        //SE la quantità totale è inferiore alla quantità richiesta, ritorna false
        connectAzienda();
        String query = "INSERT INTO Ordine(id_farmacia, id_farmaco, data_consegna, stato, quantita) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = connAzienda.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            connAzienda.setAutoCommit(false);
            stmt.setInt(1, ordine.getId_farmacia());
            stmt.setInt(2, ordine.getId_farmaco());
            stmt.setDate(3, Date.valueOf(ordine.getData_consegna()));
            stmt.setString(4, ordine.getStato());
            stmt.setInt(5, ordine.getQuantita());
            int r = stmt.executeUpdate();
            if (ordine.getStato().equalsIgnoreCase("In Attesa di disponibilita")) {
                connAzienda.commit();
                connAzienda.setAutoCommit(true);
                Main.log.info("Creato ordine In Attesa");
                return 0;
            }
            ResultSet id_ordine = stmt.getGeneratedKeys();
            if (id_ordine.next()) {
                int quantitaRimanente = queryCreaComposizioneOrdini(ordine.getId_farmaco(), ordine.getQuantita(), accettaScadenza, new Ordine(id_ordine.getInt("insert_id"), ordine));
                if (quantitaRimanente == 0) {
                    connAzienda.commit();
                    connAzienda.setAutoCommit(true);
                    Main.log.info("Creato ordine In Lavorazione");
                    return 0;
                } else {
                    connAzienda.rollback();
                    return quantitaRimanente;
                }
            }
            connAzienda.rollback();
            return -1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return -1;
        }
    }

    /**
     * Crea l'ordine componendo, se necessario, i lotti per l'ordine, settando lo stato passato come argomento
     *
     * @param ordine            ordine da creare
     * @param statoOrdine       stato dell'ordine
     * @param accettaInScadenza se l'ordine può accettare farmaci in scadenza
     * @return
     */
    public static int queryCreaOrdine(Ordine ordine, String statoOrdine, boolean accettaInScadenza) {
        ordine.setStato(statoOrdine);
        return queryCreaOrdine(ordine, accettaInScadenza);
    }

    /**
     * Query che consente a un impiegato di correggere un ordine
     * Nel particolare questo metodo fa:
     * <ol>
     *     <li>Modifica lo stato di {@code ordine} in {@code "Corretto"}</li>
     *     <li>Modifica la quantità di {@code ordine} in {@code qta_consegnata}</li>
     *     <li>Modifica la quantità di ogni lotto che compone {@code ordine}, reintegrando la quantità non consegnata</li>
     *     <li>Se {@code qty_da_integrare} è maggiore di 0, viene creato un ordine integrativo con suddetta quantità, con consegna prevista per il giorno successivo</li>
     * </ol>
     *
     * @param qty_da_integrare quantità che il farmacista vuole inviata per integrare
     * @param qta_consegnata   quantità che il farmacista vuole inviata per integrare
     * @param ordine           ordine da correggere
     * @return true if success, false if error
     */
    public static boolean queryCorreggiOrdine(int qty_da_integrare, int qta_consegnata, Ordine ordine) {
        connectAzienda();
        // SUGG: Ma qua posso fare una transazione unica? così da abbassare i tempi

        ordine.setStato("Corretto");
        /*1*/
        queryAggiornaStatoOrdine(ordine);
        /*2*/
        queryAggiornaQuantitaOrdine(ordine, qta_consegnata);
        /*3*/
        String query = "UPDATE Lotto L " +
                "SET L.quantita=L.quantita+(SELECT CO.quantita-CO.quantita_consegnata FROM ComposizioneOrdine CO WHERE CO.id_lotto = L.id_lotto AND CO.id_ordine=?) " +
                "WHERE L.id_lotto IN (SELECT CO.id_lotto FROM ComposizioneOrdine CO WHERE id_ordine = ?)";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, ordine.getId_ordine());
            stmt.setInt(2, ordine.getId_ordine());
            stmt.executeUpdate();
        } catch (Exception err) {
            erroreComunicazioneDBMS(err);
            return false;
        }
        /*4, ma senza specificare che è dello stesso lotto... impossibile*/
        if (qty_da_integrare > 0) {
            Ordine nuovoOrdine = new Ordine(
                    -1,
                    ordine.getId_farmaco(), // Lo stesso farmaco
                    ordine.getNome_farmaco(), // Parametro inutile per creare l'ordine ma a che c'ero...
                    ordine.getId_farmacia(), // Nella stessa farmacia
                    Main.orologio.chiediOrario().toLocalDate().plusDays(1), // l'ordine integrativo viene consegnato l'indomani
                    "In Lavorazione",
                    qty_da_integrare
            );
            queryCreaOrdine(nuovoOrdine, "In Lavorazione", false);
        }
        return true;
    }

    /**
     * Recupera tutti gli ordini effettuati da una farmacia, ordinati per data di consegna
     *
     * @param id_farmacia id della farmacia che fa la richiesta
     * @return {@link Ordine}[] contenente gli ordini effettuati,
     * {@code null} se non sono stati trovati risultati o errore
     */
    public static Ordine[] queryVisualizzaOrdiniFarmacia(int id_farmacia) {
        connectAzienda();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE Ordine.id_farmacia=? ORDER BY Ordine.data_consegna DESC";
        ArrayList<Ordine> ordini = new ArrayList<>();
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(Ordine.createFromDB(r));
            }
            return ordini.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }


    /**
     * Modifica la data di consegna di un ordine da parte di un farmacista
     *
     * @param ordine     ordine di cui modificare la data di consegna
     * @param nuova_data nuova data di consegna scelta dal farmacista
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaData(Ordine ordine, LocalDate nuova_data) {
        connectAzienda();
        String query = "UPDATE Ordine SET Ordine.data_consegna=? WHERE Ordine.id_ordine=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(nuova_data));
            stmt.setInt(2, ordine.getId_ordine());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini periodici di tutte le farmacie di tutte le giornate
     *
     * @return {@link OrdinePeriodico}[] contenente tutti gli ordini periodici.
     * Ritorna null se ci sono stati errori o non sono stati trovati risultati.
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici() {
        connectAzienda();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia";
        ArrayList<OrdinePeriodico> ordini = new ArrayList<>();
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            var r = stmt.executeQuery();
            while (r.next()) {
                ordini.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + ordini.size() + " ordini periodici tra tutte le farmacie in tutti i giorni");
            return ordini.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti gli ordini periodici di una determinata farmacia di tutti i giorni
     *
     * @param id_farmacia id della farmacia da cui parte la richiesta
     * @return {@link OrdinePeriodico}[] gli ordini periodici, {@code null} se non sono presenti o c'è stato errore
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(int id_farmacia) {
        connectAzienda();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia AND OrdinePeriodico.id_farmacia=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            var r = stmt.executeQuery();
            ArrayList<OrdinePeriodico> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + foo.size() + " ordini periodici per la farmacia " + id_farmacia);
            return foo.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Recupera tutti gli ordini periodici di una determinata farmacia in un determinato giorno
     *
     * @param id_farmacia id della farmacia da cui parte la richiesta
     * @param giornoDellaSettimana intero rappresentante il giorno della settimana 1 LUN, ..., 7 DOM
     * @return {@link OrdinePeriodico}[] gli ordini periodici, {@code null} se non sono presenti o c'è stato errore
     */
    public static OrdinePeriodico[] queryOrdiniPeriodici(int id_farmacia, int giornoDellaSettimana) {
        connectAzienda();
        String query = "SELECT OrdinePeriodico.*, F.nome, F2.nome FROM OrdinePeriodico, Farmacia F2 , Farmaco F WHERE F.id_farmaco = OrdinePeriodico.id_farmaco AND OrdinePeriodico.id_farmacia = F2.id_farmacia AND OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.periodicita=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setInt(2, giornoDellaSettimana);
            var r = stmt.executeQuery();
            ArrayList<OrdinePeriodico> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(OrdinePeriodico.createFromDB(r));
            }
            Main.log.debug("Trovati " + foo.size() + " ordini periodici per la farmacia " + id_farmacia + " nel giorno della settimana " + giornoDellaSettimana);
            return foo.toArray(new OrdinePeriodico[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Cerca un farmaco avendo o il nome, o il principio attivo o entrambi
     *
     * @param nome             nome farmaco
     * @param principio_attivo principio attivo farmaco
     * @return {@link Farmaco}[] array di farmaci trovati, null se nessun risultato oppure è avvenuto erroreComunicazioneDBMS;
     */
    public static Farmaco[] queryCercaFarmaco(String nome, String principio_attivo) {
        connectAzienda();
        String query = "SELECT Farmaco.* FROM Farmaco WHERE LOWER(Farmaco.nome) LIKE ? AND LOWER(Farmaco.principio_attivo) LIKE ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, "%" + nome.toLowerCase() + "%");
            stmt.setString(2, "%" + principio_attivo.toLowerCase() + "%");
            var r = stmt.executeQuery();
            ArrayList<Farmaco> farmaci = new ArrayList<>();
            while (r.next()) {
                farmaci.add(Farmaco.createFromDB(r));
            }
            return farmaci.toArray(new Farmaco[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * query per modificare la quantità di un ordine periodico da parte di un farmacista
     *
     * @param ordine    ordine periodico da modificare
     * @param nuova_qty nuova quantità da settare
     * @return 1 if success, -1 if error
     */
    public static int queryModificaOrdinePeriodico(Ordine ordine, int nuova_qty) {
        connectAzienda();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, nuova_qty);
            stmt.setInt(2, ordine.getId_farmacia());
            stmt.setInt(3, ordine.getId_farmaco());
            var r = stmt.executeUpdate();
            if (r != 0)
                return r;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera le consegne da effettuare {@code today} da parte del corriere
     * Utilizzato in {@link GestoreVisualizzaConsegne Corriere - Visualizza Consegne}
     *
     * @param data {@link LocalDate} data odierna
     * @return {@link Collo}[] tutti i colli, consegnati e non, di {@code today}
     */
    public static Collo[] queryVisualizzaConsegne(LocalDate data) {
        connectAzienda();
        ArrayList<Collo> colli = new ArrayList<>();
        String queryPrendiColli = "SELECT C.*, Farmacia.nome, Farmacia.indirizzo FROM Collo C, Farmacia WHERE Farmacia.id_farmacia = C.id_farmacia AND C.data_consegna = ?";
        String queryPrendiOrdini = "SELECT O.*, F.nome " +
                "FROM Ordine O, Collo C, Farmaco F " +
                "WHERE F.id_farmaco = O.id_farmaco " +
                "AND O.data_consegna = C.data_consegna " +
                "AND O.id_farmacia=C.id_farmacia " +
                "AND C.id_collo = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(queryPrendiColli)) {
            stmt.setDate(1, Date.valueOf(data));
            var r = stmt.executeQuery();
            PreparedStatement stmtGetOrdini = connAzienda.prepareStatement(queryPrendiOrdini);
            while (r.next()) {
                Collo c = Collo.createFromDB(r);
                colli.add(c);
                stmtGetOrdini.setInt(1, c.getId_collo());
                var rOrdini = stmtGetOrdini.executeQuery();
                while (rOrdini.next()) {
                    c.aggiungiOrdine(Ordine.createFromDB(rOrdini));
                }
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return null;
        }
        return colli.toArray(new Collo[0]);

    }

    /**
     * Firma un collo e aggiorna anche i relativi ordini contrassegnandoli con lo stato "Consegnato"
     *
     * @param firma concatenazione di nome e cognome del firmante
     * @param collo collo da firmare
     * @return true if success, false if error
     */
    public static boolean queryFirmaCollo(String firma, Collo collo) {
        // SUGG: Fare un unica transaction così abbassiamo i tempi?
        connectAzienda();
        String query = "UPDATE Collo C SET C.firma = ? WHERE C.id_collo = ?";
        String updateStatoOrdini = "UPDATE Ordine O SET O.stato = ? WHERE O.data_consegna = ? AND O.id_farmacia = ? AND LOWER(O.stato) = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setString(1, firma);
            stmt.setInt(2, collo.getId_collo());
            var res = stmt.executeUpdate();
            var stmtUpdateOrdini = connAzienda.prepareStatement(updateStatoOrdini);
            stmtUpdateOrdini.setString(1, "Consegnato");
            stmtUpdateOrdini.setDate(2, Date.valueOf(collo.getData_consegna()));
            stmtUpdateOrdini.setInt(3, collo.getId_farmacia());
            stmtUpdateOrdini.setString(4, "in lavorazione");
            var updated = stmtUpdateOrdini.executeUpdate();
            return res >= 1 && updated >= 1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return false;
    }

    /**
     * Aggiorna le scorte di un determinato giorno della settimana, nell'effettivo quindi viene utilizzato per effettuare produzione periodica
     *
     * @param today {@link LocalDate}, serve per il giorno di produzione (1 LUN, ..., 7 DOM) e per capire quando scadrà il lotto prodotto
     */
    public static void queryAggiornaScorte(LocalDate today) {
        connectAzienda();
        String query_get_produzioni = "SELECT P.* FROM ProduzionePeriodica P WHERE P.giorni_produzione = ?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query_get_produzioni)) {
            stmt.setInt(1, today.getDayOfWeek().getValue());
            var produzioni = stmt.executeQuery();
            String query_insert = "INSERT INTO Lotto (id_farmaco, data_scadenza, quantita) VALUES (?, ?, ?)";
            var updateStmt = connAzienda.prepareStatement(query_insert);
            while (produzioni.next()) {
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
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }

    }

    /**
     * Aggiorna la quantita di un ordine periodico
     *
     * @param quantita        nuova quantita
     * @param ordinePeriodico ordine periodico da modificare
     * @return 1 if success, -1 if error
     */
    public static int queryAggiornaOrdinePeriodico(int quantita, OrdinePeriodico ordinePeriodico) {
        connectAzienda();
        String query = "UPDATE OrdinePeriodico SET OrdinePeriodico.quantita=? WHERE OrdinePeriodico.id_farmacia=? AND OrdinePeriodico.id_farmaco=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, quantita);
            stmt.setInt(2, ordinePeriodico.getId_farmacia());
            stmt.setInt(3, ordinePeriodico.getId_farmaco());
            return stmt.executeUpdate() != 0 ? 1 : -1;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Recupera tutti gli ordini di una farmacia consegnati in una particolare data
     *
     * @param id_farmacia id della farmacia che fa la richiesta
     * @param data        data di consegna
     * @return {@link Ordine}[]
     */
    public static Ordine[] queryOrdini(int id_farmacia, LocalDate data) {
        connectAzienda();
        String query = "SELECT Ordine.*, F.nome FROM Ordine INNER JOIN Farmaco F on Ordine.id_farmaco = F.id_farmaco WHERE Ordine.id_farmacia=? AND Ordine.data_consegna=?";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, id_farmacia);
            stmt.setDate(2, Date.valueOf(data));
            var r = stmt.executeQuery();
            ArrayList<Ordine> foo = new ArrayList<>();
            while (r.next()) {
                foo.add(Ordine.createFromDB(r));
            }
            return foo.toArray(new Ordine[0]);
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }

    /**
     * Crea gli ordini relativi alle ordinazioni periodiche di una farmacia
     *
     * @param ordini Ordini Periodici da evadere
     * @return se la funzione va a buon fine o meno (tecnicamente non dovrebbe mai andare male)
     */
    public static boolean queryCreaOrdini(OrdinePeriodico[] ordini) {
        LocalDate data = Main.orologio.chiediOrario().toLocalDate().plusDays(1);
        connectAzienda();
        try {
            connAzienda.setAutoCommit(false);
            for (OrdinePeriodico op : ordini) {
                if(Main.orologio.chiediOrario().toLocalDate().getDayOfWeek().getValue()==op.getPeriodicita()) {
                    Ordine o = new Ordine(-1, op.getId_farmaco(), op.getNomeFarmaco(), op.getId_farmacia(), data, "In Lavorazione", op.getQuantita());
                    DBMSDaemon.queryCreaOrdine(o, false);
                }
            }
            connAzienda.commit();
            connAzienda.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
            return false;
        }
    }


    /**
     * Recupera la quantità effettivamente consegnata di un ordine
     *
     * @param ordine Ordine di cui si vuole recuperare la quantità consegnata
     * @return la quantità consegnata
     */
    public static int queryControllaQuantita(Ordine ordine) {
        String query = "SELECT CO.id_ordine, SUM(CO.quantita_consegnata) FROM ComposizioneOrdine CO WHERE CO.id_ordine=? GROUP BY CO.id_ordine";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setInt(1, ordine.getId_ordine());
            var r = stmt.executeQuery();
            if (r.next()) {
                return r.getInt(2);
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS(e);
        }
        return -1;
    }

    /**
     * Query utilizzata per poter {@link PDFCreator#creaPDF(Collo) stampare la ricevuta} del Collo avendo un ordine
     *
     * @param ordine ordine di cui recuperare il collo associato
     * @return Collo a cui appartiene l'ordine consegnato
     */
    public static Collo queryCollo(Ordine ordine) {
        connectAzienda();
        String query = "SELECT Collo.*, F.nome as nome_farmacia, F.indirizzo as indirizzo_farmacia " +
                "FROM Collo, Farmacia F " +
                "WHERE F.id_farmacia=Collo.id_farmacia " +
                "AND data_consegna= ? " +
                "AND Collo.id_farmacia= ?";
        String queryPrendiOrdini = "SELECT O.*, F.nome " +
                "FROM Ordine O, Collo C, Farmaco F " +
                "WHERE F.id_farmaco = O.id_farmaco " +
                "AND O.data_consegna = C.data_consegna " +
                "AND O.id_farmacia=C.id_farmacia " +
                "AND C.id_collo = ? ";
        try (PreparedStatement stmt = connAzienda.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(ordine.getData_consegna()));
            stmt.setInt(2, ordine.getId_farmacia());
            var r = stmt.executeQuery();
            if (r.next()) {
                Collo collo = new Collo(r.getInt(1), r.getInt(2), r.getDate(3).toLocalDate());
                collo.setFirma(r.getString(4));
                collo.setNome_farmacia(r.getString("nome_farmacia"));
                collo.setIndirizzo_farmacia(r.getString("indirizzo_farmacia"));
                // Prendo gli ordini del suddetto collo
                PreparedStatement stmt2 = connAzienda.prepareStatement(queryPrendiOrdini);
                stmt2.setInt(1, collo.getId_collo());
                var rOrdini = stmt2.executeQuery();
                while (rOrdini.next()) {
                    collo.aggiungiOrdine(Ordine.createFromDB(rOrdini));
                }
                return collo;
            }
        } catch (SQLException e) {
            erroreComunicazioneDBMS(e);
        }
        return null;
    }
}
