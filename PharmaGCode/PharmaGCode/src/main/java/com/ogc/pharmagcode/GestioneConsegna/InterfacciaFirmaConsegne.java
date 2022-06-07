package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.TableEntities.RecordOrdine;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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


    public InterfacciaFirmaConsegne(GestoreFirmaConsegne gestoreFirmaConsegne){
        this.gestoreFirmaConsegne = gestoreFirmaConsegne;
    }

    public void initialize(){
        this.listaOrdini.setItems(FXCollections.observableArrayList(this.gestoreFirmaConsegne.daFirmare.getOrdini()));
    }

    public void firmaConsegna() {
        String firma = "";
        try {
            firma = this.nomeFarmacista.getText() + " " + this.cognomeFarmacista.getText();
        }catch(Exception e){
            return;
        }
        this.gestoreFirmaConsegne.firmaCollo(firma);
    }
}
