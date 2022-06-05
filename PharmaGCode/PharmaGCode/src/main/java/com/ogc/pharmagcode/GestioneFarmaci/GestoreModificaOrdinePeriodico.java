package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.TableEntities.RecordOrdinePeriodico;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GestoreModificaOrdinePeriodico {
    private InterfacciaModificaOrdinePeriodico i;

    private OrdinePeriodico ordinePeriodico;

    public GestoreModificaOrdinePeriodico(OrdinePeriodico ordinePeriodico) {
        i = (InterfacciaModificaOrdinePeriodico) Utils.cambiaInterfaccia("GestioneFarmaci/ModificaOrdinePeriodico.fxml", new Stage(), c -> {
            return new InterfacciaModificaOrdinePeriodico(this, ordinePeriodico);
        }, 600, 400);
        this.ordinePeriodico = ordinePeriodico;
    }

    public void aggiornaQuantita(int qta, OrdinePeriodico ordinePeriodico){
        Main.log.info("Aggiornando la quantità dell'ordine periodico (" + qta + ") di " + ordinePeriodico.getNomeFarmaco() + ";" );
        DBMSDaemon.queryAggiornaOrdinePeriodico(qta, ordinePeriodico);
    }
}
