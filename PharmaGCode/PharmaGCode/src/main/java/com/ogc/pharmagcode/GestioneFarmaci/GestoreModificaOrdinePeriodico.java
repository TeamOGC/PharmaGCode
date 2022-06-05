package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaOrdinePeriodico {
    private InterfacciaModificaOrdinePeriodico i;

    public GestoreModificaOrdinePeriodico() {
        i = (InterfacciaModificaOrdinePeriodico) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdinePeriodico.fxml", new Stage(), c -> {
            return new InterfacciaModificaOrdinePeriodico(this);
        }, 600, 400);
    }
}
