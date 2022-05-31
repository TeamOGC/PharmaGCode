package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCaricoMerci {
    private InterfacciaCaricoMerci i;
    public GestoreCaricoMerci(){
        i=(InterfacciaCaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/CaricaMerci.fxml",new Stage(), c->{
            return new InterfacciaCaricoMerci(this);
        }, 600, 400);
    }

}
