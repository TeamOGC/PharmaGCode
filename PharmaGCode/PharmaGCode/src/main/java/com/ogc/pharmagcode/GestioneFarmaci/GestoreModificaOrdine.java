package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaOrdine {
    private InterfacciaModificaOrdine i;
    public GestoreModificaOrdine(){
        i=(InterfacciaModificaOrdine) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdine.fxml",new Stage(), c->{
            return new InterfacciaModificaOrdine(this);
        }, 600, 400);
    }

}
