package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GestoreCercaFarmaco {
    private InterfacciaCercaFarmaco i;
    public GestoreCercaFarmaco(){
        i=(InterfacciaCercaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/CercaFarmaco.fxml",new Stage(), c->{
            return new InterfacciaCercaFarmaco(this);
        });
    }

    public void cercaFarmaci(String nomeFarmaco, String princAttivo){
        EventHandler<ActionEvent> e=new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Ordina");
            }
        };
        ObservableList<VoceFarmaco> ol= FXCollections.observableArrayList(
                new VoceFarmaco("Tachipirina","Paracetamolo",10,true,"10/10/22","Ordina",e),
                new VoceFarmaco("Tachipirina","Paracetamolo",10,false,"10/10/22","Ordina",e),
                new VoceFarmaco("Bruscofen","Cazzoneso",1,false,"10/10/22","Ordina",e),
                new VoceFarmaco("Pippoina","Plutamolo",0,true,"10/10/22","Ordina",e)
        );
        i.aggiornaFarmaci(ol);
    }

}
