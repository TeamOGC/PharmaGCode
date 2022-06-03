package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCaricoMerci {
    private InterfacciaCaricoMerci i;
    private int id_ordine;
    public GestoreCaricoMerci(int id_ordine){
        this.id_ordine=id_ordine;
        i=(InterfacciaCaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/CaricaMerci.fxml",new Stage(), c->{
            return new InterfacciaCaricoMerci(this);
        }, 600, 400);
    }

    public void caricaFarmaco(int codiceLotto, int quantita){
        DBMSDaemon.queryCaricaFarmaco(codiceLotto, Main.idFarmacia, Main.orologio.chiediOrario().toLocalDate(), quantita);
        DBMSDaemon.queryAggiornaQuantitaConsegnataORdine(id_ordine,quantita);
    }

}
