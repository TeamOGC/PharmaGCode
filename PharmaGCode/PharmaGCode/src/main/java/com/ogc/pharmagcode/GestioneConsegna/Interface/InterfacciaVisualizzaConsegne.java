package com.ogc.pharmagcode.GestioneConsegna.Interface;

import com.ogc.pharmagcode.Common.RecordCollo;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class InterfacciaVisualizzaConsegne {
    GestoreVisualizzaConsegne gestoreVisualizzaConsegne;

    @FXML
    TableView<RecordCollo> tableColli;
    private final ObservableList<RecordCollo> listaColli;

    public InterfacciaVisualizzaConsegne(GestoreVisualizzaConsegne gestoreVisualizzaConsegne) {
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
        this.listaColli = FXCollections.observableList(GestoreVisualizzaConsegne.listaColli);
    }

    public void initialize() {
        this.tableColli.setItems(this.listaColli);
    }

}
