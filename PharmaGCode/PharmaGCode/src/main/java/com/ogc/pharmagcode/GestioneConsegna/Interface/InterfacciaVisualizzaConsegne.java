package com.ogc.pharmagcode.GestioneConsegna.Interface;

import com.ogc.pharmagcode.Common.RecordCollo;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreVisualizzaConsegne;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class InterfacciaVisualizzaConsegne {
    GestoreVisualizzaConsegne gestoreVisualizzaConsegne;

    @FXML
    TableView<RecordCollo> listaConsegne;

    public InterfacciaVisualizzaConsegne(GestoreVisualizzaConsegne gestoreVisualizzaConsegne){
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
    }

    public void initialize(){
        this.listaConsegne.setItems(gestoreVisualizzaConsegne.observableColli);
    }

    public void aggiornaTabella(){
        this.listaConsegne.setItems(gestoreVisualizzaConsegne.observableColli);
        this.listaConsegne.refresh();
    }

}
