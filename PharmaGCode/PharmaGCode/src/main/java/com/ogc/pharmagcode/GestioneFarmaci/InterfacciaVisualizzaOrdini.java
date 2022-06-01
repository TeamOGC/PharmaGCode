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
        EventHandler<ActionEvent> carica = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliccaCarica();
            }
        };

        EventHandler<ActionEvent> modifica = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliccaModifica();
            }
        };

        ol=FXCollections.observableArrayList(
                new VoceOrdine("Tachipirina", "Consegnato", "10","10/05/22", "", modifica),
                new VoceOrdine("Gaviscon", "In lavorazione", "10","10/06/22", "Modifica", modifica),
                new VoceOrdine("Oki", "Corretto", "10","22/05/22", "", modifica),
                new VoceOrdine("Moment", "In spedizione", "10","01/06/22", "Carica", carica)
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

    @FXML
    public void cliccaCarica(){ new GestoreCaricoMerci(); }

    @FXML
    public void cliccaModifica(){ new GestoreModificaOrdine(); }
}
