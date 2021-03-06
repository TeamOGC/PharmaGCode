package com.ogc.pharmagcode.GestioneAzienda.Interface;

import com.ogc.pharmagcode.Common.InterfacciaPrincipale;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreModificaProduzione;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreVisualizzaOrdiniAzienda;
import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InterfacciaPrincipaleImpiegato extends InterfacciaPrincipale {
    BoundaryDiSistemaImpiegato b=new BoundaryDiSistemaImpiegato();
    public InterfacciaPrincipaleImpiegato(String nome, String cognome, String id_azienda) {
        super(nome + " " + cognome );
    }

    @FXML
    public void initialize(){
        super.initialize();
        Main.orologio.setOrologio(e->{
            b.chiediData();
        });
    }
    @FXML
    public void cliccaVisualizzaOrdini() {
        Main.log.debug("Impiegato ha cliccato su visualizza ordini azienda");
        new GestoreVisualizzaOrdiniAzienda();
    }

    public void cliccaModificaProduzione() {
        Main.log.debug("Impiegato ha cliccato su modifica produzione");
        new GestoreModificaProduzione();
    }
}
