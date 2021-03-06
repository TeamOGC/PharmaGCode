package com.ogc.pharmagcode.Autenticazione.Control;

import com.ogc.pharmagcode.Autenticazione.Interface.ModuloLogin;
import com.ogc.pharmagcode.Entity.Corriere;
import com.ogc.pharmagcode.Entity.Impiegato;
import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaPrincipaleImpiegato;
import com.ogc.pharmagcode.GestioneConsegna.Interface.InterfacciaPrincipaleCorriere;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaPrincipaleFarmacista;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreAutenticazione {
    private static Utente utente;
    private static Utente def = new Utente("Giovanni", "Muciaccia", "test1@sium.it");
    private static Utente def2 = new Corriere(11, "Giovanni", "Muciaccia", "test1@sium.it", "PasswordTest");
    private static Utente def3 = new Impiegato(22, "Giovanni", "Muciaccia", "test1@sium.it", "PasswordTest");

    public GestoreAutenticazione(Stage stage) {
        if (Main.debug)
            switch (Main.sistema) {
                case 0 -> utente = def;
                case 1 -> utente = def2;
                case 2 -> utente = def3;
            }
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml", stage, c -> {
            return new ModuloLogin(this);
        });
    }

    /**
     * Controlla le credenziali ed effettua il Login
     *
     * <ul>
     *     <li>Se {@systemProperty Main.debug} allora accetta anche credenziali vuote</li>
     *     <li>Altrimenti chiama {@link DBMSDaemon#queryControllaCredenziali(String, String) DBMSDaemon.queryControllaCredenziali(email, Hash(password)}</li>
     * </ul>
     * @param email email dell'utente
     * @param password password in chiaro dell'utente, viene hashata in questo metodo.
     * @param s Stage in cui creare le {@link com.ogc.pharmagcode.Common.InterfacciaPrincipale InterfacciaPrincipale[tipo="Main.sistema"]}
     */
    public void controlloCredenziali(String email, String password, Stage s) {
        if ((email.isBlank() || password.isBlank()) && !Main.debug) {
            Utils.creaPannelloErrore("Inserisci tutti i dati");
            return;
        }
        if (utente == null) { // Pu?? non esserlo solo se siamo in debug
            utente = DBMSDaemon.queryControllaCredenziali(email, Utils.hash(password));
            if (utente == null) {
                Utils.creaPannelloErrore("Credenziali inserite errate, ritenta");
                return;
            }
        }
        if (Main.sistema == 0) {
            Utils.cambiaInterfaccia("GestioneFarmaci/InterfacciaFarmacista.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleFarmacista(GestoreAutenticazione.utente.nome(), GestoreAutenticazione.utente.cognome());
                    });
        } else if (Main.sistema == 1) {
            Corriere corriere = (Corriere) utente;
            Utils.cambiaInterfaccia("GestioneConsegna/InterfacciaCorriere.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleCorriere(corriere.nome(), corriere.cognome());
                    });
        } else if (Main.sistema == 2) {
            Impiegato impiegato = (Impiegato) utente;
            Utils.cambiaInterfaccia("GestioneOrdini/InterfacciaAzienda.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleImpiegato(impiegato.nome(), impiegato.cognome(), impiegato.getId_impiegato() + "");
                    });
        }
    }

    /**
     * @return {@link Utente} autenticato
     */
    public static Utente getUtente() {
        return utente;
    }
}
