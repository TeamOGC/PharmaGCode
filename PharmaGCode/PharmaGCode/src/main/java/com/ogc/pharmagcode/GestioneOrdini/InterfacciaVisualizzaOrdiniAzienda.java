package com.ogc.pharmagcode.GestioneOrdini;

import com.ogc.pharmagcode.Utils.RecordLista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class InterfacciaVisualizzaOrdiniAzienda {

    @FXML
    public ListView<RecordLista> listaOrdiniAzienda;

    private final ArrayList<RecordLista> ordini;

    public InterfacciaVisualizzaOrdiniAzienda(ArrayList<RecordLista> listaOrdiniAzienda){
        this.ordini = listaOrdiniAzienda;
    }
    @FXML
    public void initialize(){
        listaOrdiniAzienda.setFixedCellSize(55);
        ObservableList<RecordLista> ol= FXCollections.observableArrayList(this.ordini);
        this.listaOrdiniAzienda.setItems(ol);
    }


}
