package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaConsegne {
    public GestoreVisualizzaConsegne(Stage s){
        Utils.cambiaInterfaccia("GestioneConsegna/VisualizzaConsegne.fxml", s, c->{ return new InterfacciaVisualizzaConsegne(this);});
    }
}
