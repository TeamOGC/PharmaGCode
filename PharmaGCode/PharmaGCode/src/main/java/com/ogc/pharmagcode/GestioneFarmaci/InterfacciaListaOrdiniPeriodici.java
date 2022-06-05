package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;

public class InterfacciaListaOrdiniPeriodici {
    GestoreListaOrdiniPeriodici gestoreListaOrdiniPeriodici;
    @FXML
    private ListView<RecordLista> listaOrdiniPeriodici;

    ObservableList<RecordLista> ol;
    public InterfacciaListaOrdiniPeriodici(GestoreListaOrdiniPeriodici gestoreListaOrdiniPeriodici){
        this.gestoreListaOrdiniPeriodici = gestoreListaOrdiniPeriodici;
    }
    @FXML
    protected void initialize(){

        listaOrdiniPeriodici.setFixedCellSize(55);

        EventHandler<ActionEvent> modifica = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cliccaModifica();
            }
        };

        ol= FXCollections.observableArrayList(
                new RecordLista(modifica,984,"Tachipirina", "Consegnato", "10","10/05/22", "Modifica"),
                new RecordLista(modifica,984,"Gaviscon", "In lavorazione", "10","10/06/22", "Modifica"),
                new RecordLista(modifica,984,"Oki", "Corretto", "10","22/05/22", "Modifica"),
                new RecordLista(modifica,984,"Moment", "In spedizione", "10","01/06/22", "Modifica")
        );

        listaOrdiniPeriodici.setItems(ol);
    }
    public void AggiornaOrdini(ArrayList<RecordLista> vo){
        ol=FXCollections.observableArrayList(vo);
        listaOrdiniPeriodici.setItems(ol);
    }

    @FXML
    public void cliccaModifica(){ new GestoreModificaOrdinePeriodico(); }
}
