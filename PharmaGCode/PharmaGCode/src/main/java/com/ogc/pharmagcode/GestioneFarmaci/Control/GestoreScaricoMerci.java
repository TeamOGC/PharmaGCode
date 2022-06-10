package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaScaricoMerci;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreScaricoMerci {
    private InterfacciaScaricoMerci i;

    public GestoreScaricoMerci() {
        i = (InterfacciaScaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/ScaricaMerci.fxml", new Stage(), c -> {
            return new InterfacciaScaricoMerci(this);
        }, 600, 400);
    }

    public void scaricoMerci(int codiceLotto, int quantita) {
        int s = DBMSDaemon.queryScaricaMerci(codiceLotto, Main.idFarmacia, quantita);
        if (s <= 5) {
            Utils.creaPannelloConferma("ATTENZIONE: il farmaco scaricato è in esaurimento!");
        }
    }
}
