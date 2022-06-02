package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreFirmaConsegne {
    public GestoreFirmaConsegne(Stage s){
        Utils.cambiaInterfaccia("GestioneConsegna/FirmaConsegne.fxml", s, c->{ return new InterfacciaFirmaConsegne(this);});
    }
}
