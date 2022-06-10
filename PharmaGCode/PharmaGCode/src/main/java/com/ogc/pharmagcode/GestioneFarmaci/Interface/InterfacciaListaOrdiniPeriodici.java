package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Common.RecordOrdinePeriodico;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreListaOrdiniPeriodici;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class InterfacciaListaOrdiniPeriodici {
    @FXML
    public TextField cercaNome;
    GestoreListaOrdiniPeriodici gestoreListaOrdiniPeriodici;
    @FXML
    private TableView<RecordOrdinePeriodico> listaOrdiniPeriodici;


    ObservableList<RecordOrdinePeriodico> ol;

    public InterfacciaListaOrdiniPeriodici(GestoreListaOrdiniPeriodici gestoreListaOrdiniPeriodici) {
        this.gestoreListaOrdiniPeriodici = gestoreListaOrdiniPeriodici;
    }

    @FXML
    protected void initialize() {
        listaOrdiniPeriodici.setItems(this.gestoreListaOrdiniPeriodici.recordOrdinePeriodicoObservableList);
    }

    @FXML
    public void aggiornaOrdiniPeriodici() {
        String nomeDaCercare = cercaNome.getText();
        List<RecordOrdinePeriodico> localList;
        localList = this.gestoreListaOrdiniPeriodici.recordOrdinePeriodicoObservableList
                .stream()
                .filter(recordOrdinePeriodico -> (
                        recordOrdinePeriodico.getNomeFarmaco().toLowerCase().contains(nomeDaCercare.toLowerCase())) || (nomeDaCercare.isBlank()) )
                .toList();
        ObservableList<RecordOrdinePeriodico> localObservableList = FXCollections.observableArrayList(localList);
        listaOrdiniPeriodici.setItems(localObservableList);
    }
}
