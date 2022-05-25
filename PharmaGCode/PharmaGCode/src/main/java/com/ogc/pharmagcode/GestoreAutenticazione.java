package com.ogc.pharmagcode;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestoreAutenticazione {
    public GestoreAutenticazione(Stage stage){
        Utils.cambiaInterfaccia("GestioneAccount/Login.fxml",stage, c->{return new ModuloLogin(this);});
    }

    public void controlloCredenziali(String email, String password){

    }

    public void creaPannelloErrore(){ //da cambiare l'accesso
        Utils.creaPannelloErrore("NOOOOO");
    }

    public void sostituisciInterfaccia(){

    }

    public void creaInterfacciaPrincipale(Stage stage){
    }
}
