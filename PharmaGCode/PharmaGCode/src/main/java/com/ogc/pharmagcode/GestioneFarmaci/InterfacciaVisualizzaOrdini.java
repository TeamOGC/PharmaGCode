package com.ogc.pharmagcode.GestioneFarmaci;

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
    private ListView<VoceOrdine> listaOrdini;

    ObservableList<VoceOrdine> ol;
    public InterfacciaVisualizzaOrdini(){

    }
    @FXML
    protected void initialize(){
        listaOrdini.setFixedCellSize(55);
        EventHandler<ActionEvent> e=new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Carica");
            }
        };
        ol=FXCollections.observableArrayList(
                new VoceOrdine("Tachipirina", "Paracetamolo", "10","10/10/10", "15/10/10", "Carica", e),
                new VoceOrdine("Tachipirina", "Paracetamolo", "10","10/10/10", "15/10/10", "Carica", e),
                new VoceOrdine("Tachipirina", "Paracetamolo", "10","10/10/10", "15/10/10", "Carica", e),
                new VoceOrdine("Tachipirina", "Paracetamolo", "10","10/10/10", "15/10/10", "Carica", e)
        );

        listaOrdini.setItems(ol);
    }
    public void AggiornaOrdini(ArrayList<VoceOrdine> vo){
        ol=FXCollections.observableArrayList(vo);
        listaOrdini.setItems(ol);
    }
    @FXML
    public void cliccaAnnulla(){

    }
}
