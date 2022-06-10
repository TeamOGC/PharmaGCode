package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Utils.TableEntities.RecordCollo;
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
