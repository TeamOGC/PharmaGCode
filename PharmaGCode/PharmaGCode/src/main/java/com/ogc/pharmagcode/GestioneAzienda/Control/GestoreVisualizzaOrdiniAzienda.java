package com.ogc.pharmagcode.GestioneAzienda.Control;

import com.ogc.pharmagcode.Common.RecordOrdine;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneAzienda.Interface.InterfacciaVisualizzaOrdiniAzienda;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class GestoreVisualizzaOrdiniAzienda {
    private InterfacciaVisualizzaOrdiniAzienda i;
    private final static ArrayList<RecordOrdine> listaOrdiniAzienda = new ArrayList<>();

    public GestoreVisualizzaOrdiniAzienda() {
        Ordine[] ordini = DBMSDaemon.queryVisualizzaOrdiniAzienda();
        if (ordini != null) {
            listaOrdiniAzienda.addAll(Arrays.stream(ordini).map(RecordOrdine::fromOrdine).toList());
        }
        this.i = (InterfacciaVisualizzaOrdiniAzienda) Utils.cambiaInterfaccia("GestioneOrdini/VisualizzaOrdiniAzienda.fxml", new Stage(), c -> new InterfacciaVisualizzaOrdiniAzienda(listaOrdiniAzienda));
    }

    /**
     * Aggiorna {@link GestoreVisualizzaOrdiniAzienda#listaOrdiniAzienda} rimuovendo (se presente un ordine con lo stesso id) ed inserendo nuovamente {@code aggiornato}
     * In questo modo la tabella viene aggiornata
     *
     * @param aggiornato l'ordine da rimuovere e inserire
     */
    protected static void aggiornaTabella(Ordine aggiornato) {
        listaOrdiniAzienda.removeIf(recordOrdine -> recordOrdine.getId_ordine() == aggiornato.getId_ordine());
        listaOrdiniAzienda.add(0, RecordOrdine.fromOrdine(aggiornato));
    }
}
