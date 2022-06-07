package com.ogc.pharmagcode.Utils.TableEntities;

import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Entity.Ordine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecordCollo extends Collo {

    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;
    private Button bottone;

    public RecordCollo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, Ordine[] ordini, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_collo, id_farmacia, data_consegna, firma);
        super.aggiungiOrdini(ordini);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.initializeButton();
    }

    public RecordCollo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_collo, id_farmacia, data_consegna, firma);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.initializeButton();
    }

    private void initializeButton(){
        if(nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }

    public static RecordCollo fromCollo(Collo collo, String nomeBottone, EventHandler<ActionEvent> callback){
        return new RecordCollo(
                collo.getId_collo(),
                collo.getId_farmacia(),
                collo.getData_consegna(),
                collo.getFirma(),
                collo.getOrdini().toArray(new Ordine[0]),
                nomeBottone,
                callback
        );
    }
}
