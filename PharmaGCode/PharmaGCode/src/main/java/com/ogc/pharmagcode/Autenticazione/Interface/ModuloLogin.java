package com.ogc.pharmagcode.Autenticazione.Interface;

import com.ogc.pharmagcode.Autenticazione.Control.GestoreAutenticazione;
import com.ogc.pharmagcode.Autenticazione.Control.GestoreRecuperaCredenziali;
import com.ogc.pharmagcode.Autenticazione.Control.GestoreRegistrazione;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloLogin {
    GestoreAutenticazione gestoreAutenticazione;
    Stage s;
    public ModuloLogin(GestoreAutenticazione gestoreAutenticazione){
        this.gestoreAutenticazione=gestoreAutenticazione;        // System.out.println("cliccaAccedi "+email.getText()+ " "+password.getText());

    }
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    protected void cliccaAccedi(){
        gestoreAutenticazione.controlloCredenziali(email.getText(), password.getText(), (Stage) email.getScene().getWindow());
    }
    @FXML
    protected void cliccaRecuperaCredenziali(){
        new GestoreRecuperaCredenziali();
    }
    @FXML
    protected void cliccaRegistrati(){
        new GestoreRegistrazione();
    }
}
