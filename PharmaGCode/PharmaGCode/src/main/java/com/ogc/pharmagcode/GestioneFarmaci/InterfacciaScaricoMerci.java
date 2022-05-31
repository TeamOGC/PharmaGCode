package com.ogc.pharmagcode.GestioneFarmaci;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InterfacciaScaricoMerci {

    private GestoreScaricoMerci gestoreScaricoMerci;

    @FXML
    private TextField lotto;

    @FXML
    private TextField quantita;

    public InterfacciaScaricoMerci(GestoreScaricoMerci gestoreScaricoMerci){
        this.gestoreScaricoMerci = gestoreScaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
