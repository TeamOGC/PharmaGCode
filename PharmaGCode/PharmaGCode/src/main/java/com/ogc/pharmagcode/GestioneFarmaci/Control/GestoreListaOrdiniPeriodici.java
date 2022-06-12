package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Common.RecordOrdinePeriodico;
import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaListaOrdiniPeriodici;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreListaOrdiniPeriodici {

    public ObservableList<RecordOrdinePeriodico> recordOrdinePeriodicoObservableList;

    public GestoreListaOrdiniPeriodici() {

        ArrayList<RecordOrdinePeriodico> ordiniPeriodici = new ArrayList<>();
        OrdinePeriodico[] ordini = DBMSDaemon.queryOrdiniPeriodici(Main.idFarmacia);

        if (ordini != null) {
            for (OrdinePeriodico ordine : ordini) {
                Main.log.info(ordine);
                ordiniPeriodici.add(RecordOrdinePeriodico.fromOrdinePeriodico(ordine));
            }
        }
        recordOrdinePeriodicoObservableList = FXCollections.observableList(ordiniPeriodici);

        Utils.cambiaInterfaccia("GestioneFarmaci/ListaOrdiniPeriodici.fxml", new Stage(), c -> new InterfacciaListaOrdiniPeriodici(this));
    }
}
