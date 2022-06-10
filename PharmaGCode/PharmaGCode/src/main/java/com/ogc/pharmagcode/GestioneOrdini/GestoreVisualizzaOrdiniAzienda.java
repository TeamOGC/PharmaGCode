package com.ogc.pharmagcode.GestioneOrdini;

import com.itextpdf.text.DocumentException;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneOrdini.InterfacciaVisualizzaOrdiniAzienda;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.PDFCreator;
import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GestoreVisualizzaOrdiniAzienda {
    private InterfacciaVisualizzaOrdiniAzienda i;
    private ArrayList<RecordLista> listaOrdiniAzienda = new ArrayList<>();

    public GestoreVisualizzaOrdiniAzienda(){

        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniAzienda();
        if (ordini != null) {
            Main.log.info("Ordini trovati " + ordini.length);
            // farmaco | qta | farmacia | dataConsegna | stato | stato == da verificare ? bottone : niente

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
                else if (ordine.getStato().equalsIgnoreCase("consegnato")){
                    e = creaPDF ->{
                        try {
                            PDFCreator.creaPDF(DBMSDaemon.queryCollo(ordine));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (DocumentException ex) {
                            throw new RuntimeException(ex);
                        }
                    };
                    bottone = "Ricevuta";

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

        this.i=(InterfacciaVisualizzaOrdiniAzienda) Utils.cambiaInterfaccia("GestioneOrdini/VisualizzaOrdiniAzienda.fxml",
                new Stage(),
                c->{return new InterfacciaVisualizzaOrdiniAzienda(ordini);});
    }
}
