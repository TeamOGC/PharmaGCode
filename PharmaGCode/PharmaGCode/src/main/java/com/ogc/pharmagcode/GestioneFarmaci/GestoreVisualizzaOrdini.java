package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaOrdini {
    public GestoreVisualizzaOrdini(Stage s){
        Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml",s, c->{return new InterfacciaVisualizzaOrdini();});

    }


}
