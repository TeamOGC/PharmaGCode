package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.skin.DatePickerSkin;

public class InterfacciaModificaOrdine {
    GestoreModificaOrdine gestoreModificaOrdine;

    @FXML
    private TextField quantita;


    public InterfacciaModificaOrdine(GestoreModificaOrdine gestoreModificaOrdine){
        this.gestoreModificaOrdine = gestoreModificaOrdine;
    }

    @FXML
    public void initialize(){
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public void conferma(ActionEvent actionEvent) {
    }
}
