package com.ogc.pharmagcode.GestioneAccount;

import com.ogc.pharmagcode.GestioneFarmaci.InterfacciaPrincipaleFarmacista;
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
        s = (Stage) email.getScene().getWindow();
        System.out.println("cliccaAccedi "+email.getText()+ " "+password.getText());
        Utils.cambiaInterfaccia("GestioneFarmaci/InterfacciaFarmacista.fxml",s
                ,c->{return new InterfacciaPrincipaleFarmacista("Francesco","Giorgio","12345");
        });
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
