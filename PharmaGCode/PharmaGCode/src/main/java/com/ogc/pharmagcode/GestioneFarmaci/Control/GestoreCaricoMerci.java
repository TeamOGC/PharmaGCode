package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaCaricoMerci;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCaricoMerci {
    private InterfacciaCaricoMerci i;
    private int id_ordine;
    private Stage stage;



    public GestoreCaricoMerci(int id_ordine) {
        this.stage= new Stage();
        this.id_ordine = id_ordine;
        i = (InterfacciaCaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/CaricaMerci.fxml", this.stage, c -> {
            return new InterfacciaCaricoMerci(this);
        }, 600, 400);
    }

    public void caricaFarmaco(int codiceLotto, int quantita) {
        Main.log.info("Caricando lotto (" + codiceLotto + ") quantita (" + quantita + ")  --- DB NON zÈ COMMENTATO");
        DBMSDaemon.queryCaricaFarmaco(codiceLotto, Main.idFarmacia, Main.orologio.chiediOrario().toLocalDate(), quantita);
        DBMSDaemon.aggiornaQuantitaConsegnataOrdine(id_ordine, codiceLotto, quantita);
        Utils.creaPannelloConferma("Merce Caricata Correttamente", this.stage);
    }

}
