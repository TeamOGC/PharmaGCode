package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreModificaOrdinePeriodico;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class InterfacciaModificaOrdinePeriodico {
    GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico;

    @FXML
    private TextField quantita;

    private OrdinePeriodico ordinePeriodico;

    @FXML
    public void initialize() {
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.positiveIntegerFilter));
    }

    public InterfacciaModificaOrdinePeriodico(GestoreModificaOrdinePeriodico gestoreModificaOrdinePeriodico, OrdinePeriodico ordinePeriodico) {
        this.gestoreModificaOrdinePeriodico = gestoreModificaOrdinePeriodico;
        this.ordinePeriodico = ordinePeriodico;
    }

    public void conferma(ActionEvent actionEvent) {

        if (quantita.getText().isBlank()) return;
        int qta = Integer.parseInt(quantita.getText());
        this.gestoreModificaOrdinePeriodico.aggiornaQuantita(qta, ordinePeriodico);
        ((Stage) this.quantita.getScene().getWindow()).close();
    }
}
