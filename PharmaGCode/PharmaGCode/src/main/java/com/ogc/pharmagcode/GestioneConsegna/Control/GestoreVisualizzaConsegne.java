package com.ogc.pharmagcode.GestioneConsegna.Control;

import com.ogc.pharmagcode.Common.RecordCollo;
import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.GestioneConsegna.Interface.InterfacciaVisualizzaConsegne;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreVisualizzaConsegne {

    public static ArrayList<RecordCollo> listaColli = new ArrayList<>();

    public GestoreVisualizzaConsegne() {
        Stage stage = new Stage();
        chiediConsegne();
        Utils.cambiaInterfaccia("GestioneConsegna/VisualizzaConsegne.fxml", stage, c -> new InterfacciaVisualizzaConsegne(this));
    }


    private void chiediConsegne() {
        listaColli = new ArrayList<>();
        Collo[] colli = DBMSDaemon.queryVisualizzaConsegne(Main.orologio.chiediOrario().toLocalDate());
        if (colli != null) {
            Main.log.info("Colli per oggi trovati: " + colli.length);
            for (Collo collo : colli) {
                listaColli.add(RecordCollo.fromCollo(collo));
            }
        }
    }

    /**
     * Questo metodo serve solamente a poter aggiornare la lista dopo aver firmato, senza dover contattare il DB
     * @param aggiornato RecordCollo del collo aggiornato, senza nessun bottone e con la nuova firma
     */
    protected static void aggiornaTabella(RecordCollo aggiornato){
        listaColli.removeIf(recordCollo -> recordCollo.getId_collo() == aggiornato.getId_collo());
        listaColli.add(aggiornato);
    }
}
