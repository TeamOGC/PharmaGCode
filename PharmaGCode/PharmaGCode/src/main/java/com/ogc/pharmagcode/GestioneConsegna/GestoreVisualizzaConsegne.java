package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Common.RecordCollo;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GestoreVisualizzaConsegne {

    ObservableList<RecordCollo> observableColli;
    InterfacciaVisualizzaConsegne boundary;

    public GestoreVisualizzaConsegne(Stage s) {
        this.boundary = (InterfacciaVisualizzaConsegne) Utils.cambiaInterfaccia("GestioneConsegna/VisualizzaConsegne.fxml", s, c -> {
            return new InterfacciaVisualizzaConsegne(this);
        });
        chiediConsegne();

    }

    public void chiediConsegne(){
        ArrayList<RecordCollo> records = new ArrayList<>();
        Collo[] colli = DBMSDaemon.queryVisualizzaConsegne(Main.orologio.chiediOrario().toLocalDate());
        if (colli != null) {
            Main.log.info("Colli per oggi trovati: " + colli.length);
            for (Collo collo : colli) {
                RecordCollo toAdd;
                if (collo.getFirma().isBlank()) {
                    toAdd =
                            RecordCollo.fromCollo(
                                    collo,
                                    "Firma",
                                    firma -> {
                                        new GestoreFirmaConsegne(new Stage(), this, collo);
                                    });
                } else {
                    toAdd = RecordCollo.fromCollo(
                            collo, "", null
                    );
                }
                records.add(toAdd);
            }
        }
        observableColli = FXCollections.observableArrayList(records);
        boundary.aggiornaTabella();
    }
}
