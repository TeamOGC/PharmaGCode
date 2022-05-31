package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.Entity.Utente;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreAutenticazione {
    private static Utente utente;
    public GestoreAutenticazione(Stage stage){
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml",stage, c->{return new ModuloLogin(this);});
    }
    public static Utente getUtente(){
        return utente;
    }
    public boolean controlloCredenziali(String email, String password){
        utente= DBMSDaemon.F_ControllaCredenziali(email, password);
        return utente!=null;
    }

    public void creaPannelloErrore(){ //da cambiare l'accesso
        Utils.creaPannelloErrore("NOOOOO");
    }

    public void sostituisciInterfaccia(){

    }

    public void creaInterfacciaPrincipale(Stage stage){
    }
}
