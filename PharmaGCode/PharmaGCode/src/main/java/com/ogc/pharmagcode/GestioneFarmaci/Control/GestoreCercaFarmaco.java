package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Common.RecordFarmaco;
import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaCercaFarmaco;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreCercaFarmaco {
    private InterfacciaCercaFarmaco i;

    public GestoreCercaFarmaco() {
        i = (InterfacciaCercaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/CercaFarmaco.fxml", new Stage(), c -> {
            return new InterfacciaCercaFarmaco(this);
        });
        cercaFarmaci("", "");
    }

    public void cercaFarmaci(String nomeFarmaco, String princAttivo) {
        Farmaco[] farmaci = DBMSDaemon.querycercaFarmaco(nomeFarmaco, princAttivo);
        if (farmaci == null) return;
        DBMSDaemon.queryQuantitaFarmaci(farmaci, Main.idFarmacia);
        ArrayList<RecordFarmaco> listaFarmaci = new ArrayList<>();
        for (Farmaco f : farmaci) {
            listaFarmaci.add(RecordFarmaco.fromFarmaco(f, "Ordina", ordina -> new GestoreOrdinaFarmaco(f)));
        }
        i.aggiornaFarmaci(listaFarmaci);
    }
}
