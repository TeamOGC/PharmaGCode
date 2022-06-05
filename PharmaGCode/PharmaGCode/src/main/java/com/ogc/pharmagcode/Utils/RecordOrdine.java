package com.ogc.pharmagcode.Utils;

import com.ogc.pharmagcode.Entity.Ordine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.time.LocalDate;

public class RecordOrdine extends Ordine {
    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;

    private Button bottone = null;

    public RecordOrdine(int id_ordine, int id_farmaco, String nome_farmaco, int id_farmacia, LocalDate data_consegna, String stato, int quantita, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_ordine, id_farmaco, nome_farmaco, id_farmacia, data_consegna, stato, quantita);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        if(nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
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

    public static RecordOrdine fromOrdine(Ordine ordine, String nomeBottone, EventHandler<ActionEvent> callback){
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

    public static RecordOrdine fromOrdine(Ordine ordine){
        return new RecordOrdine(
                ordine.getId_ordine(),
                ordine.getId_farmaco(),
                ordine.getNome_farmaco(),
                ordine.getId_farmacia(),
                ordine.getData_consegna(),
                ordine.getStato(),
                ordine.getQuantita(),
                null,
                null
        );
    }
}
