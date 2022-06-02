package com.ogc.pharmagcode.GestioneConsegna;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InterfacciaFirmaConsegne {
    GestoreFirmaConsegne gestoreFirmaConsegne;

    @FXML
    private TextField nomeFarmacista;

    @FXML
    private TextField cognomeFarmacista;


    public InterfacciaFirmaConsegne(GestoreFirmaConsegne gestoreFirmaConsegne){
        this.gestoreFirmaConsegne = gestoreFirmaConsegne;
    }

    public void firmaConsegna(ActionEvent actionEvent) {
    }
}
