package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Common.RecordFarmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreCercaFarmaco;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InterfacciaCercaFarmaco {
    private final GestoreCercaFarmaco gestoreCercaFarmaco;
    @FXML
    private TextField nomeFarmaco;
    @FXML
    private TextField princAttivo;
    @FXML
    private TableView<RecordFarmaco> listaFarmaci;

    public InterfacciaCercaFarmaco(GestoreCercaFarmaco gestoreCercaFarmaco) {
        this.gestoreCercaFarmaco = gestoreCercaFarmaco;
    }

    @FXML
    protected void initialize() {
//        listaFarmaci.setFixedCellSize(55);
    }

    @FXML
    protected void conferma() {
        gestoreCercaFarmaco.cercaFarmaci(nomeFarmaco.getText(), princAttivo.getText());
    }

    public void aggiornaFarmaci(ArrayList<RecordFarmaco> ol) {
        listaFarmaci.setItems(FXCollections.observableArrayList(ol));
    }
}
