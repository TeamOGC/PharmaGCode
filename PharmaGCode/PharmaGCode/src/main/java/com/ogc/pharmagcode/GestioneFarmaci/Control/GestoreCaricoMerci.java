package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaCaricoMerci;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreCaricoMerci {
    private InterfacciaCaricoMerci i;
    private Ordine ordine;
    private Stage stage;



    public GestoreCaricoMerci(Ordine ordine) {
        this.stage= new Stage();
        this.ordine = ordine;
        i = (InterfacciaCaricoMerci) Utils.cambiaInterfaccia("GestioneFarmaci/CaricaMerci.fxml", this.stage, c -> {
            return new InterfacciaCaricoMerci(this);
        }, 600, 400);
    }

    /**
     * Carica il lotto
     *
     * @param codiceLotto codice del lotto da caricare
     * @param quantita quantità da caricare
     */
    public void caricaFarmaco(int codiceLotto, int quantita) {
        Main.log.info("Caricando lotto (" + codiceLotto + ") quantita (" + quantita + ")  --- DB NON zÈ COMMENTATO");

        if(DBMSDaemon.aggiornaQuantitaConsegnataOrdine(ordine.getId_ordine(), codiceLotto, quantita)) {
            DBMSDaemon.queryCaricaFarmaco(codiceLotto, Main.idFarmacia, quantita, ordine.getId_farmaco());
            Utils.creaPannelloConferma("Merce Caricata Correttamente", this.stage);
        }
        else{
            Utils.creaPannelloErrore("Il lotto inserito non fa parte dell'ordine selezionato");
        }
    }

}
