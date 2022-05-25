package com.ogc.pharmagcode;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloLogin {
    GestoreAutenticazione gestoreAutenticazione;
    Stage s;
    public ModuloLogin(GestoreAutenticazione gestoreAutenticazione){
        this.gestoreAutenticazione=gestoreAutenticazione;
    }
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    protected void cliccaAccedi(){
        System.out.println("cliccaAccedi "+email.getText()+ " "+password.getText());
    }
    @FXML
    protected void cliccaRecuperaCredenziali(){
        System.out.println("Funziona");
        gestoreAutenticazione.creaPannelloErrore();
    }
    @FXML
    protected void cliccaRegistrati(){
        s = (Stage) email.getScene().getWindow();
        new GestoreRegistrazione(s);
    }
}
