package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaOrdini {
    private InterfacciaVisualizzaOrdini i;
    public GestoreVisualizzaOrdini(){
        i=(InterfacciaVisualizzaOrdini) Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml",
                new Stage(),
                c->{return new InterfacciaVisualizzaOrdini();});

    }


}
