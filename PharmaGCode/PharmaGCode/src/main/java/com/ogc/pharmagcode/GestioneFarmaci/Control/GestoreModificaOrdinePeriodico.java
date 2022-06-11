package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaModificaOrdinePeriodico;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreModificaOrdinePeriodico {
    private InterfacciaModificaOrdinePeriodico i;
    private Stage stage;

    private OrdinePeriodico ordinePeriodico;

    public GestoreModificaOrdinePeriodico(OrdinePeriodico ordinePeriodico) {
        this.stage = new Stage();
        i = (InterfacciaModificaOrdinePeriodico) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdinePeriodico.fxml", this.stage, c -> {
            return new InterfacciaModificaOrdinePeriodico(this, ordinePeriodico);
        }, 600, 400);
        this.ordinePeriodico = ordinePeriodico;
    }

    /**
     * Aggiorna la quantità dell'ordine periodico
     * Al termine mostra un {@link com.ogc.pharmagcode.Common.PannelloConferma Pannello di Conferma}, alla chiusura di quest'ultimo verrà chiuso anche il pannello di modifica
     *
     * @param qta nuova quantità dell'ordine periodico
     * @param ordinePeriodico l'ordine periodico da modificare
     */
    public void aggiornaQuantita(int qta, OrdinePeriodico ordinePeriodico) {
        Main.log.info("Aggiornando la quantità dell'ordine periodico (" + qta + ") di " + ordinePeriodico.getNomeFarmaco() + ";");
        DBMSDaemon.queryAggiornaOrdinePeriodico(qta, ordinePeriodico);
        Utils.creaPannelloConferma("Quantità modificata correttamente", this.stage);
    }
}
