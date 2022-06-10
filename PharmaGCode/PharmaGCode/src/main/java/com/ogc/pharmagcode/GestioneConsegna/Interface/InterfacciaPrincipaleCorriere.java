package com.ogc.pharmagcode.GestioneConsegna.Interface;

import com.ogc.pharmagcode.Common.InterfacciaPrincipale;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleCorriere extends InterfacciaPrincipale {

    public InterfacciaPrincipaleCorriere(String nome, String cognome, String idCorriere) {
        super(nome + " " + cognome + " Corriere: " + idCorriere);
    }

    @FXML
    public void cliccaVisualizzaConsegne() {
        new GestoreVisualizzaConsegne();
    }
}
