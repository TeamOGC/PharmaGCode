package com.ogc.pharmagcode.GestioneOrdini;

import com.ogc.pharmagcode.GestioneProduzione.GestoreModificaProduzione;
import com.ogc.pharmagcode.Common.InterfacciaPrincipale;
import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterfacciaPrincipaleImpiegato extends InterfacciaPrincipale {

    public InterfacciaPrincipaleImpiegato(String nome, String cognome, String idAzienda){
        super(nome + " " + cognome + " Azienda: " + idAzienda);
    }

    @FXML
    public void cliccaVisualizzaOrdini(ActionEvent actionEvent) {
        Main.log.debug("Impiegato ha cliccato su visualizza ordini azienda");

        new GestoreVisualizzaOrdiniAzienda();
    }

    public void cliccaModificaProduzione(ActionEvent actionEvent) {
        Main.log.debug("Impiegato ha cliccato su modifica produzione");
        new GestoreModificaProduzione();
    }
}
