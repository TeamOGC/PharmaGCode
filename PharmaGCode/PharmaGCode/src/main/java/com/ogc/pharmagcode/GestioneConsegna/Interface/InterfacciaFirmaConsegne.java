package com.ogc.pharmagcode.GestioneConsegna.Interface;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneConsegna.Control.GestoreFirmaConsegne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InterfacciaFirmaConsegne {
    public TableView<Ordine> listaOrdini;
    GestoreFirmaConsegne gestoreFirmaConsegne;

    @FXML
    private TextField nomeFarmacista;

    @FXML
    private TextField cognomeFarmacista;


    public InterfacciaFirmaConsegne(GestoreFirmaConsegne gestoreFirmaConsegne) {
        this.gestoreFirmaConsegne = gestoreFirmaConsegne;
    }

    public void initialize() {
        this.listaOrdini.setItems(FXCollections.observableList(this.gestoreFirmaConsegne.daFirmare.getOrdini()));
    }

    public void firmaConsegna() {
        String firma = this.nomeFarmacista.getText() + " " + this.cognomeFarmacista.getText();
        this.gestoreFirmaConsegne.firmaCollo(firma);
    }
}
