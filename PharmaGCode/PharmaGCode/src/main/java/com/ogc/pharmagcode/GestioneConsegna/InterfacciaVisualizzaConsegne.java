package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.GestioneFarmaci.GestoreModificaOrdine;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaVisualizzaConsegne {
    GestoreVisualizzaConsegne gestoreVisualizzaConsegne;

//    @FXML
//    ListView<VoceConsegna> listaConsegne;

    public InterfacciaVisualizzaConsegne(GestoreVisualizzaConsegne gestoreVisualizzaConsegne){
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
    }

}
