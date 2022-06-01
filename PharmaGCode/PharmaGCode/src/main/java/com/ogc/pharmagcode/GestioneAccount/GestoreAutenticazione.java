package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.GestioneConsegna.InterfacciaPrincipaleCorriere;
import com.ogc.pharmagcode.GestioneFarmaci.InterfacciaPrincipaleFarmacista;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreAutenticazione {
    private static Utente utente;
    public GestoreAutenticazione(Stage stage){
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml",stage, c->{return new ModuloLogin(this);});
    }
    public void controlloCredenziali(String email, String password, Stage s) {
        if (Main.sistema == 0) {
            utente=DBMSDaemon.F_ControllaCredenziali(email,Utils.hash(password));
            if(utente!=null)
                Utils.cambiaInterfaccia("GestioneFarmaci/InterfacciaFarmacista.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleFarmacista(GestoreAutenticazione.utente.nome(),GestoreAutenticazione.utente.cognome());
                    });
            else
                Utils.creaPannelloErrore("Credenziali non corrette");

        } else if (Main.sistema == 1) {
            Utils.cambiaInterfaccia("GestioneConsegna/InterfacciaCorriere.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleCorriere(GestoreAutenticazione.utente.nome(),GestoreAutenticazione.utente.cognome(), "22");
                    });
        } else if (Main.sistema == 2) {
            Utils.cambiaInterfaccia("GestioneProduzione/InterfacciaImpiegato.fxml", s
                    , c -> {
                        return new InterfacciaPrincipaleCorriere(GestoreAutenticazione.utente.nome(),GestoreAutenticazione.utente.cognome(), "11");
                    });
        }
    }
    public static Utente getUtente(){
        return utente;
    }

    public void creaPannelloErrore(){ //da cambiare l'accesso
        Utils.creaPannelloErrore("NOOOOO");
    }

    public void sostituisciInterfaccia(){

    }

    public void creaInterfacciaPrincipale(Stage stage){
    }
}
