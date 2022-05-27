package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCercaFarmaco {
    private InterfacciaCercaFarmaco i;
    public GestoreCercaFarmaco(){
        i=(InterfacciaCercaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/CercaFarmaco.fxml",new Stage(), c->{
            return new InterfacciaCercaFarmaco(this);
        });
    }

    public void cercaFarmaci(String nomeFarmaco, String princAttivo){

    }

}
