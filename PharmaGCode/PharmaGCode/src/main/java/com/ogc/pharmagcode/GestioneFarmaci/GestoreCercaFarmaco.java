package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.TableEntities.RecordFarmaco;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.ArrayList;

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
                Main.log.info("Clickato su Ordina");
            }
        };
        Farmaco[] farmaci= DBMSDaemon.querycercaFarmaco(nomeFarmaco,princAttivo);
        DBMSDaemon.queryQuantitaFarmaci(farmaci, Main.idFarmacia);
        ArrayList<RecordFarmaco> listaFarmaci=new ArrayList<>();

        for(Farmaco f:farmaci){
            EventHandler<ActionEvent> callback = ordina -> {new GestoreOrdinaFarmaco(f);};
            listaFarmaci.add(
                    RecordFarmaco.fromFarmaco(f, "Ordina", callback)
            );
        }
        i.aggiornaFarmaci(listaFarmaci);
    };

}
