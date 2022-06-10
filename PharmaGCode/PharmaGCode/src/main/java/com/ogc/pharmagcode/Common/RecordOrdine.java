package com.ogc.pharmagcode.Common;

import com.itextpdf.text.DocumentException;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.PDFCreator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Questa classe viene utilizzata esclusivamente all'interno dentro una TableView
 */
public class RecordOrdine extends Ordine {
    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;

    private Button bottone = null;

    public RecordOrdine(int id_ordine, int id_farmaco, String nome_farmaco, int id_farmacia, LocalDate data_consegna, String stato, int quantita, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }

    public String getNomeBottone() {
        return nomeBottone;
    }

    public EventHandler<ActionEvent> getCallback() {
        return callback;
    }

    public Button getBottone() {
        return bottone;
    }

    public static RecordOrdine fromOrdine(Ordine ordine, String nomeBottone, EventHandler<ActionEvent> callback) {
        return new RecordOrdine(
                ordine.getId_ordine(),
                ordine.getId_farmaco(),
                ordine.getNome_farmaco(),
                ordine.getId_farmacia(),
                ordine.getData_consegna(),
                ordine.getStato(),
                ordine.getQuantita(),
                nomeBottone,
                callback
        );
    }

    public static RecordOrdine fromOrdine(Ordine ordine) {
        String nomeBottone = "";
        EventHandler<ActionEvent> callback = null;
        if(ordine.getStato().equalsIgnoreCase("consegnato")){
            nomeBottone = "Ricevuta";
            callback = creaPDF -> {
                try {
                    PDFCreator.creaPDF((DBMSDaemon.queryCollo(ordine)));
                } catch (IOException | DocumentException e) {
                    Main.log.error("Problema con il PDF", e);
                    throw new RuntimeException(e);
                }
                Main.log.info("Cliccato sul bottone Ricevuta");
            };
        } else if (ordine.getStato().equalsIgnoreCase("da verificare")) {
            nomeBottone = "Correggi";
            callback = correggi -> {
                Main.log.info("Cliccato sul bottone correggi");
                new GestoreCorrezioneOrdine(ordine);
            };
        }
        return new RecordOrdine(
                ordine.getId_ordine(),
                ordine.getId_farmaco(),
                ordine.getNome_farmaco(),
                ordine.getId_farmacia(),
                ordine.getData_consegna(),
                ordine.getStato(),
                ordine.getQuantita(),
                nomeBottone,
                callback
        );
    }
}
