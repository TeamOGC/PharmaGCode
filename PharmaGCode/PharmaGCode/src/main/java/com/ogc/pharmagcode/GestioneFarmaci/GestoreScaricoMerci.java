package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreScaricoMerci {
    private InterfacciaScaricoMerci i;
    public GestoreScaricoMerci(){
        i=(InterfacciaScaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/ScaricaMerci.fxml",new Stage(), c->{
            return new InterfacciaScaricoMerci(this);
        }, 600, 400);
    }

}
