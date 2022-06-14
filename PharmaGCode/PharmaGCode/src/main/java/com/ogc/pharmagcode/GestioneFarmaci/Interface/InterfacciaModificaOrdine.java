package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreModificaOrdine;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaModificaOrdine {
    @FXML private DatePicker dataconsegna;
    GestoreModificaOrdine gestoreModificaOrdine;

    @FXML
    private TextField quantita;

    private final Ordine ordine;

    public InterfacciaModificaOrdine(GestoreModificaOrdine gestoreModificaOrdine, Ordine ordine) {
        this.gestoreModificaOrdine = gestoreModificaOrdine;
        this.ordine = ordine;
    }

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.positiveIntegerFilter));
        quantita.setText(ordine.getQuantita() + "");
        dataconsegna.setValue(ordine.getData_consegna());

    }

    public void conferma() {
        gestoreModificaOrdine.modificaOrdine(Integer.parseInt(quantita.getText()), dataconsegna.getValue());
    }
}
