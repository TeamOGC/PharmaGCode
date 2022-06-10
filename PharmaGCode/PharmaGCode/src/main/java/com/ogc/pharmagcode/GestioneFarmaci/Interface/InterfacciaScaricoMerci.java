package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreScaricoMerci;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaScaricoMerci {

    private GestoreScaricoMerci gestoreScaricoMerci;

    @FXML
    private TextField lotto;

    @FXML
    private TextField quantita;

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
        this.lotto.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaScaricoMerci(GestoreScaricoMerci gestoreScaricoMerci) {
        this.gestoreScaricoMerci = gestoreScaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
