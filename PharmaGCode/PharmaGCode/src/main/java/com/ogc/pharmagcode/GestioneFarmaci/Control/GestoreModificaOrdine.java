package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaModificaOrdine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreModificaOrdine {
    private final Ordine ordine;

    private final Stage stage;

    public GestoreModificaOrdine(Ordine ordine) {
        this.ordine = ordine;
        this.stage = new Stage();
        Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdine.fxml", this.stage, c -> new InterfacciaModificaOrdine(this, ordine), 600, 400);
    }

    public void modificaOrdine(int nuovaQuantita, LocalDate data) {
        if (data.isBefore(Main.orologio.chiediOrario().toLocalDate().plusDays(2))) {
            Utils.creaPannelloErrore("La data di consegna non può essere prima di " + Main.orologio.chiediOrario().toLocalDate().plusDays(2));
            return;
        }
        if (!data.isEqual(ordine.getData_consegna())) {
            Main.log.info("Modificando la data dell'ordine");
            DBMSDaemon.queryAggiornaData(ordine, data);
        }
        if( nuovaQuantita != ordine.getQuantita()){
            Main.log.info("Modificando la quantità dell'ordine");
            if (DBMSDaemon.queryAggiornaQuantitaOrdine(ordine, nuovaQuantita) == -1) {
                Utils.creaPannelloErrore("Errore durante la modifica della quantità dell'ordine");
                return;
            }
        }
        Utils.creaPannelloConferma("Ordine Modificato Correttamente", this.stage);
    }
}
