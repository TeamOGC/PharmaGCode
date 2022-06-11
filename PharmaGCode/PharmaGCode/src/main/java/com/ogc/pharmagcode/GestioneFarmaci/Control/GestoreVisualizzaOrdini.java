package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Common.RecordOrdine;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaVisualizzaOrdini;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestoreVisualizzaOrdini {
    private InterfacciaVisualizzaOrdini i;
    private LocalDate d;

    public GestoreVisualizzaOrdini() {
        d = Main.orologio.chiediOrario().toLocalDate();
        i = (InterfacciaVisualizzaOrdini) Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml",
                new Stage(),
                c -> {
                    return new InterfacciaVisualizzaOrdini(this);
                });
    }

    public ArrayList<RecordOrdine> chiediOrdini() {
        ArrayList<RecordOrdine> listaOrdini = new ArrayList<>();
        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniFarmacia(Main.idFarmacia);
        if (ordini != null) {
            for (Ordine o : ordini) {
                listaOrdini.add(RecordOrdine.fromOrdine(o));
            }
        }
        return listaOrdini;
    }
}
