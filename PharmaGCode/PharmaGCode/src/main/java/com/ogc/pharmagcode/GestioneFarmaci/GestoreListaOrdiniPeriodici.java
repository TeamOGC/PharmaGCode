package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreListaOrdiniPeriodici {
    private InterfacciaListaOrdiniPeriodici i;
    public GestoreListaOrdiniPeriodici(){
        i=(InterfacciaListaOrdiniPeriodici) Utils.cambiaInterfaccia("GestioneFarmaci/ListaOrdiniPeriodici.fxml",new Stage(), c->{
            return new InterfacciaListaOrdiniPeriodici(this);
        });
    }
}
