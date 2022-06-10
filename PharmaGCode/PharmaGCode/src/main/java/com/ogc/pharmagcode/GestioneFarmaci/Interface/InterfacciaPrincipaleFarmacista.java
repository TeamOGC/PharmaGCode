package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Common.InterfacciaPrincipale;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreCercaFarmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreListaOrdiniPeriodici;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreScaricoMerci;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreVisualizzaOrdini;
import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterfacciaPrincipaleFarmacista extends InterfacciaPrincipale {

    public InterfacciaPrincipaleFarmacista(String nome, String cognome) {
        super(nome + " " + cognome + " " + " Farmacia: " + Main.idFarmacia);
        Main.log.info("Gestione Farmaci");
    }

    @FXML
    protected void cliccaCercaFarmaco() {
        new GestoreCercaFarmaco();
    }

    @FXML
    private void cliccaVisualizzaOrdini() {
        new GestoreVisualizzaOrdini();
    }

    @FXML
    protected void cliccaScaricoMerci() {
        new GestoreScaricoMerci();
    }

    public void cliccaModificaOrdinePeriodico(ActionEvent actionEvent) {
        new GestoreListaOrdiniPeriodici();
    }
}
