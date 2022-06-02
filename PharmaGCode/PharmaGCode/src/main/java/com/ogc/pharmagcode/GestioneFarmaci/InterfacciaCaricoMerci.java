package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaCaricoMerci {

    private GestoreCaricoMerci gestoreCaricoMerci;

    @FXML
    private TextField lotto;

    @FXML
    private TextField quantita;

    @FXML
    public void initialize(){
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaCaricoMerci(GestoreCaricoMerci gestoreCaricoMerci){
        this.gestoreCaricoMerci = gestoreCaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
