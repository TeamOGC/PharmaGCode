package com.ogc.pharmagcode;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModuloRegistrazione {
    GestoreRegistrazione gestoreRegistrazione;
    public ModuloRegistrazione(GestoreRegistrazione gestoreRegistrazione){
        this.gestoreRegistrazione = gestoreRegistrazione;
    }

    private Stage s;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confermaPassword;
    @FXML
    private TextField campoOTP;
    @FXML
    private VBox moduloOTP;
    @FXML
    protected void initialize(){
        moduloOTP.setVisible(false);
    }
    @FXML
    protected void cliccaRegistrati(){
        System.out.println("cliccaRegistrati");
        gestoreRegistrazione.inviaMailOTP(email.getText());
        moduloOTP.setVisible(true);
    }
    @FXML
    protected void annulla(){
        s = (Stage) email.getScene().getWindow();
        new GestoreAutenticazione(s);
    }
    @FXML
    protected void cliccaConferma(){
        gestoreRegistrazione.controllaValiditaOTP(campoOTP.getText());
        // Metodo Verifica OTP
    }
}
