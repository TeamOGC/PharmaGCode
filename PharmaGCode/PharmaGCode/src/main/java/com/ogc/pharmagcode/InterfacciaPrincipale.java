package com.ogc.pharmagcode;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InterfacciaPrincipale {
    String nomeMostrato;
    @FXML
    protected Label nomeUtente;

    public InterfacciaPrincipale(String nomeUtente){
        nomeMostrato=nomeUtente;
    }

    @FXML
    protected void initialize(){
        nomeUtente.setText(nomeMostrato);
    }

    @FXML
    protected void cliccaModificaPassword(){
        //crea InterfacciaModificaPassword
    }
}
