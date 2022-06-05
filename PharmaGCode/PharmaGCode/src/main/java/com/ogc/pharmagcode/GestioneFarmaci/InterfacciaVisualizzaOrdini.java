package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.RecordLista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class InterfacciaVisualizzaOrdini {
    @FXML
    private ListView<RecordLista> listaOrdini;
    GestoreVisualizzaOrdini gestoreVisualizzaOrdini;

    ObservableList<RecordLista> ol;
    public InterfacciaVisualizzaOrdini(GestoreVisualizzaOrdini gestoreVisualizzaOrdini){
        this.gestoreVisualizzaOrdini=gestoreVisualizzaOrdini;
    }
    @FXML
    protected void initialize(){
        listaOrdini.setFixedCellSize(55);
        EventHandler<ActionEvent> carica = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliccaCarica(-1);
            }
        };

        EventHandler<ActionEvent> modifica = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliccaModifica();
            }
        };

        ol=FXCollections.observableArrayList( gestoreVisualizzaOrdini.chiediOrdini());

        listaOrdini.setItems(ol);
    }
    public void AggiornaOrdini(ArrayList<RecordLista> vo){
        ol=FXCollections.observableArrayList(vo);
        listaOrdini.setItems(ol);
    }
    @FXML
    public void cliccaAnnulla(){

    }

    @FXML
    public void cliccaCarica(int id_ordine){ new GestoreCaricoMerci( id_ordine); }

    @FXML
    public void cliccaModifica(){ }
}
