package com.ogc.pharmagcode.GestioneProduzione;

import com.ogc.pharmagcode.GestioneConsegna.InterfacciaPrincipaleCorriere;
import com.ogc.pharmagcode.InterfacciaPrincipale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleImpiegato extends InterfacciaPrincipale {

    public InterfacciaPrincipaleImpiegato(String nome, String cognome, String idAzienda){
        super(nome + " " + cognome + " Azienda: " + idAzienda);
    }

    @FXML
    public void cliccaVisualizzaOrdiniAzienda(){
        new GestoreVisualizzaOrdiniAzienda();
    }

    public void cliccaVisualizzaOrdini(ActionEvent actionEvent) {
    }

    public void cliccaModificaProduzione(ActionEvent actionEvent) {
    }
}
