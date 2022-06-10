package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreModificaOrdine;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaModificaOrdine {
    GestoreModificaOrdine gestoreModificaOrdine;

    @FXML
    private TextField quantita;

    private Ordine ordine;

    public InterfacciaModificaOrdine(GestoreModificaOrdine gestoreModificaOrdine, Ordine ordine) {
        this.gestoreModificaOrdine = gestoreModificaOrdine;
        this.ordine = ordine;
    }

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
        quantita.setText(ordine.getQuantita() + "");

    }

    public void conferma(ActionEvent actionEvent) {
        gestoreModificaOrdine.modificaOrdine(Integer.parseInt(quantita.getText()));
        Utils.creaPannelloConferma("Ordine Modificato Correttamente");
    }
}
