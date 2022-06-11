package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaModificaOrdine;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaOrdine {
    private InterfacciaModificaOrdine i;
    private Ordine ordine;

    private final Stage stage;

    public GestoreModificaOrdine(Ordine ordine) {
        this.ordine = ordine;
        this.stage = new Stage();
        i = (InterfacciaModificaOrdine) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdine.fxml", this.stage, c -> {
            return new InterfacciaModificaOrdine(this, ordine);
        }, 600, 400);
    }

    public void modificaOrdine(int nuovaQuantita) { //LocalDate date
        if (DBMSDaemon.queryAggiornaQuantitaOrdine(ordine, nuovaQuantita) == -1)
            Utils.creaPannelloErrore("Errore");
        else {
            // TODO: Buffa Aggiorna tabella GestoreVisualizzaOrdini
            this.stage.close();
        }
    }
}
