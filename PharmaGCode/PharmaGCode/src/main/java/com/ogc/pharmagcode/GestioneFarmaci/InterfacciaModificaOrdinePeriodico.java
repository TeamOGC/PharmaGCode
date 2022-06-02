package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaModificaOrdinePeriodico {
    GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico;

    @FXML
    private TextField quantita;

    @FXML
    public void initialize(){
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaModificaOrdinePeriodico(GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico){
        this.gestoreModificaOrdinePeriodico = gestoreModificaOrdinePeriodico;
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
