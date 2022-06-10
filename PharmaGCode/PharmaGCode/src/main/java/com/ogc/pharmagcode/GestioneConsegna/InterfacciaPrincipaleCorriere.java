package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Common.InterfacciaPrincipale;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleCorriere extends InterfacciaPrincipale {

    public InterfacciaPrincipaleCorriere(String nome, String cognome, String idCorriere){
        super(nome + " " + cognome + " Corriere: " + idCorriere);
    }

    @FXML
    public void cliccaVisualizzaConsegne(){
        new GestoreVisualizzaConsegne(new Stage());
    }
}
