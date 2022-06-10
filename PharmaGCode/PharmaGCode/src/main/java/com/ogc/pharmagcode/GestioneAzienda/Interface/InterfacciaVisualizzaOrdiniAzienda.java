package com.ogc.pharmagcode.GestioneAzienda.Interface;

import com.itextpdf.text.DocumentException;
import com.ogc.pharmagcode.Common.RecordOrdine;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.PDFCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterfacciaVisualizzaOrdiniAzienda {


    public TableView<RecordOrdine> tableViewOrdini;
    private final ObservableList<RecordOrdine> observableOrdini;

    public InterfacciaVisualizzaOrdiniAzienda(ArrayList<RecordOrdine> listaOrdiniAzienda) {
        this.observableOrdini = FXCollections.observableList(listaOrdiniAzienda);
    }

    @FXML
    public void initialize() {
        this.tableViewOrdini.setItems(this.observableOrdini);
    }
}
