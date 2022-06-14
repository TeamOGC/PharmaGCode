package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Common.RecordOrdine;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaVisualizzaOrdini;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestoreVisualizzaOrdini {

    private final static ArrayList<RecordOrdine> listaOrdini = new ArrayList<>();


    public GestoreVisualizzaOrdini() {
        listaOrdini.clear();
        Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml", new Stage(), c -> new InterfacciaVisualizzaOrdini(this));
    }

    public ArrayList<RecordOrdine> chiediOrdini() {
        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniFarmacia(Main.idFarmacia);
        var merceCaricata = DBMSDaemon.queryMerceCaricata(Main.idFarmacia, Main.orologio.chiediOrario().toLocalDate());
        if (merceCaricata == null) merceCaricata = new HashMap<>();
        if (ordini != null) {
            for (Ordine o : ordini) {
                if (o.getData_consegna().isEqual(Main.orologio.chiediOrario().toLocalDate()) && (o.getStato().equalsIgnoreCase("consegnato")))
                    if (merceCaricata.get(o.getId_farmaco()) != null && merceCaricata.get(o.getId_farmaco()) == o.getQuantita()) {
                        Button btn = new Button("Caricato!");
                        btn.setDisable(true);
                        listaOrdini.add(new RecordOrdine(o, btn));
                        continue;
                    }
                listaOrdini.add(RecordOrdine.fromOrdine(o));
            }
        }
        return listaOrdini;
    }

    protected static void aggiornaOrdine(Ordine aggiornato){
        listaOrdini.removeIf(recordOrdine -> recordOrdine.getId_ordine() == aggiornato.getId_ordine());
        if(aggiornato.getQuantita() != 0)
            listaOrdini.add(0, RecordOrdine.fromOrdine(aggiornato));
    }
}
