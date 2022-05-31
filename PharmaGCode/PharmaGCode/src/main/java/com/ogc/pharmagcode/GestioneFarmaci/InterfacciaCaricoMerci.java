package com.ogc.pharmagcode.GestioneFarmaci;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InterfacciaCaricoMerci {

    private GestoreCaricoMerci gestoreCaricoMerci;

    @FXML
    private TextField lotto;

    @FXML
    private TextField quantita;

    public InterfacciaCaricoMerci(GestoreCaricoMerci gestoreCaricoMerci){
        this.gestoreCaricoMerci = gestoreCaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
