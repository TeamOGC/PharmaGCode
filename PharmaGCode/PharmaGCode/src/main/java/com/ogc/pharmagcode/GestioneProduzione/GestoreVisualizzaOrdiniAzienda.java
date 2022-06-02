package com.ogc.pharmagcode.GestioneProduzione;

import com.ogc.pharmagcode.GestioneFarmaci.InterfacciaVisualizzaOrdini;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaOrdiniAzienda {
    private InterfacciaVisualizzaOrdiniAzienda i;

    public GestoreVisualizzaOrdiniAzienda(){
        i=(InterfacciaVisualizzaOrdiniAzienda) Utils.cambiaInterfaccia("GestioneProduzione/VisualizzaOrdiniAzienda.fxml",
                new Stage(),
                c->{return new InterfacciaVisualizzaOrdiniAzienda();});

    }
}
