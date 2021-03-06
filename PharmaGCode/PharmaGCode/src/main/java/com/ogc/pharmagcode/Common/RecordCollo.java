package com.ogc.pharmagcode.Common;

import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreFirmaConsegne;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.time.LocalDate;

/**
 * Questa classe serve a poter visualizzare un {@link Collo} all'interno di una {@link javafx.scene.control.TableView tabella JavaFX}
 * <p>
 * In particolare è possibile aggiungere un bottone con un {@link RecordCollo#nomeBottone suo testo} ed una {@link RecordCollo#callback sua funzione}
 */
public class RecordCollo extends Collo {

    private final String nomeBottone;
    private final EventHandler<ActionEvent> callback;
    private Button bottone;

    public RecordCollo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, String nome_farmacia, String indirizzo_farmacia, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_collo, id_farmacia, data_consegna, firma, nome_farmacia, indirizzo_farmacia);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.initializeButton();
    }

    public RecordCollo(int id_collo, int id_farmacia, LocalDate data_consegna, String firma, String nome_farmacia, String indirizzo_farmacia, Ordine[] ordini, String nomeBottone, EventHandler<ActionEvent> callback) {
        this(id_collo, id_farmacia, data_consegna, firma, nome_farmacia, indirizzo_farmacia, nomeBottone, callback);
        super.aggiungiOrdini(ordini);
    }


    private void initializeButton() {
        if (nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btnlist");
            bottone.setMaxHeight(10);
        }
    }


    /**
     * Crea un Entity RecordCollo avendo un Collo
     * Se {@link Collo#getFirma() la firma del collo} è vuota aggiunge il bottone di Firma
     *
     * @param collo collo
     * @return RecordCollo collo con il pulsante, se la firma è vuota
     */
    public static RecordCollo fromCollo(Collo collo){
        String nomeBottone = "";
        EventHandler<ActionEvent> callback = null;
        if(collo.getFirma().isBlank()){
            nomeBottone="Firma";
            callback = firma -> new GestoreFirmaConsegne(collo);
            }
        return new RecordCollo(collo.getId_collo(), collo.getId_farmacia(), collo.getData_consegna(), collo.getFirma(), collo.getNome_farmacia(), collo.getIndirizzo_farmacia(), collo.getOrdini().toArray(new Ordine[0]), nomeBottone, callback);
    }

    public Button getBottone() {
        return bottone;
    }
}
