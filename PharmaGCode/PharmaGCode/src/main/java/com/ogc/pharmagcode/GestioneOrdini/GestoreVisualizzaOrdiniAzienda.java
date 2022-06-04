package com.ogc.pharmagcode.GestioneOrdini;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneOrdini.InterfacciaVisualizzaOrdiniAzienda;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreVisualizzaOrdiniAzienda {
    private InterfacciaVisualizzaOrdiniAzienda i;
    private ArrayList<RecordLista> listaOrdiniAzienda = new ArrayList<>();

    public GestoreVisualizzaOrdiniAzienda(){

        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniAzienda();
        if (ordini != null) {
            Main.log.info("Ordini trovati " + ordini.length);
            // farmaco | qta | farmcia | dataConsegna | stato | stato == da verificare ? bottone : niente

            for (Ordine ordine : ordini) {
                String bottone = "";
                EventHandler<ActionEvent> e = n -> {
                };
                if (ordine.getStato().equalsIgnoreCase("da verificare")) {
                    e = correggi -> {
                        new GestoreCorrezioneOrdine(ordine);
                    };
                    bottone = "Correggi";
                }
                listaOrdiniAzienda.add(
                        new RecordLista(e, 984,
                                ordine.getNome_farmaco(),
                                ordine.getQuantita() + "",
                                ordine.getId_farmacia() + "",
                                ordine.getData_consegna().toString(),
                                ordine.getStato(),
                                bottone
                        )
                );

            }
        }
        else Main.log.warn("Non sono stati trovati ordini");

        this.i=(InterfacciaVisualizzaOrdiniAzienda) Utils.cambiaInterfaccia("GestioneProduzione/VisualizzaOrdiniAzienda.fxml",
                new Stage(),
                c->{return new InterfacciaVisualizzaOrdiniAzienda(listaOrdiniAzienda);});
    }
}
