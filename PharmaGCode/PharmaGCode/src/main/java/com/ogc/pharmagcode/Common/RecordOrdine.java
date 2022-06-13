package com.ogc.pharmagcode.Common;

import com.itextpdf.text.DocumentException;
import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreCorrezioneOrdine;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreCaricoMerci;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreModificaOrdine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.PDFCreator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Questa classe serve a poter visualizzare un {@link Collo} all'interno di una {@link javafx.scene.control.TableView tabella JavaFX}
 * <p>
 * In particolare è possibile aggiungere un bottone con un {@link RecordCollo#nomeBottone suo testo} ed una {@link RecordCollo#callback sua funzione}
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

    public static RecordOrdine fromOrdine(Ordine ordine, String nomeBottone, EventHandler<ActionEvent> callback) { // TODO: Usare l'altro che aggiunge i bottoni in automatico
        return new RecordOrdine(ordine.getId_ordine(), ordine.getId_farmaco(), ordine.getNome_farmaco(), ordine.getId_farmacia(), ordine.getData_consegna(), ordine.getStato(), ordine.getQuantita(), nomeBottone, callback);
    }

    /**
     * Aggiunge automaticamente un bottone in base all'ordine passato
     * <ul>
     *     <li>In Farmacia
     *         <ul>
     *             <li>Data di consegna > 2 giorni da oggi: {@link GestoreModificaOrdine#modificaOrdine(int, LocalDate)  Modifica}</li>
     *             <li>Data di consegna è oggi && Stato = Consegnato: {@link GestoreCaricoMerci#caricaFarmaco(int, int)  Carica}</li>
     *         </ul>
     *     </li>
     *     <li>In Azienda
     *          <ul>
     *              <li>Stato = Consegnato: {@link PDFCreator#creaPDF(Collo) Ricevuta}</li>
     *              <li>Stato = Da verificare: {@link GestoreCorrezioneOrdine#correggiOrdine(int) Correggi}</li>
     *          </ul>
     *      </li>
     * </ul>
     *
     * @param ordine Ordine da inserire in una tabella
     * @return RecordOrdine con il pulsante opportuno
     */
    public static RecordOrdine fromOrdine(Ordine ordine) {
        String nomeBottone = "";
        EventHandler<ActionEvent> callback = null;
        if (Main.sistema == 0) { // Lato Farmacia i bottoni sono Modifica o Carica
            LocalDate d = Main.orologio.chiediOrario().toLocalDate();
            if (Duration.between(d.atTime(0, 0, 1), ordine.getData_consegna().atTime(0, 0, 1)).toDays() > 1) {
                nomeBottone = "Modifica";
                callback = modifica -> {
                    new GestoreModificaOrdine(ordine);
                };
            } else if (d.atTime(0, 0, 1).equals(ordine.getData_consegna().atTime(0, 0, 1)) && ordine.getStato().equalsIgnoreCase("consegnato")) {
                nomeBottone = "Carica";
                callback = carica -> {
                    new GestoreCaricoMerci(ordine);
                };
            }
        } else if (Main.sistema == 2) { // Lato azienda i bottoni sono: Correggi e Ricevuta
            if (ordine.getStato().equalsIgnoreCase("consegnato")) {
                nomeBottone = "Ricevuta";
                callback = creaPDF -> {
                    Main.log.info("Cliccato sul bottone Ricevuta");
                    try {
                        Collo collo = DBMSDaemon.queryCollo(ordine);
                        if (collo == null) Main.log.warn("Qualcosa è andato storto e non avrebbe dovuto, riprova");
                        else PDFCreator.creaPDF(collo);
                    } catch (IOException | DocumentException e) {
                        Main.log.error("Problema con il PDF", e);
                    }
                };
            } else if (ordine.getStato().equalsIgnoreCase("da verificare")) {
                nomeBottone = "Correggi";
                callback = correggi -> {
                    Main.log.info("Cliccato sul bottone correggi");
                    new GestoreCorrezioneOrdine(ordine);
                };
            }
        }
        return new RecordOrdine(ordine.getId_ordine(), ordine.getId_farmaco(), ordine.getNome_farmaco(), ordine.getId_farmacia(), ordine.getData_consegna(), ordine.getStato(), ordine.getQuantita(), nomeBottone, callback);
    }
}
