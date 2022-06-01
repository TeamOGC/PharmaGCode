package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.GestioneConsegna.InterfacciaPrincipaleCorriere;
import com.ogc.pharmagcode.GestioneFarmaci.InterfacciaPrincipaleFarmacista;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
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
        // System.out.println("cliccaAccedi "+email.getText()+ " "+password.getText());
        gestoreAutenticazione.controlloCredenziali(email.getText(), password.getText(), (Stage) email.getScene().getWindow());
    }
    @FXML
    protected void cliccaRecuperaCredenziali(){
        System.out.println("Funziona");
        //gestoreAutenticazione.creaPannelloErrore();
        new GestoreRecuperaCredenziali();
    }
    @FXML
    protected void cliccaRegistrati(){
        new GestoreRegistrazione(new Stage());
    }
}
