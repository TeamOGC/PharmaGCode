package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Common.RecordOrdine;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreCaricoMerci;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreVisualizzaOrdini;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class InterfacciaVisualizzaOrdini {

    @FXML private TextField nomeFarmacoFilter;
    @FXML private DatePicker dataConsegnaFilter;
    @FXML
    private TableView<RecordOrdine> listaOrdini;
    GestoreVisualizzaOrdini gestoreVisualizzaOrdini;

    ObservableList<RecordOrdine> ol;

    public InterfacciaVisualizzaOrdini(GestoreVisualizzaOrdini gestoreVisualizzaOrdini) {
        this.gestoreVisualizzaOrdini = gestoreVisualizzaOrdini;
    }

    @FXML
    protected void initialize() {
        ol = FXCollections.observableList(gestoreVisualizzaOrdini.chiediOrdini());
        listaOrdini.setItems(ol);
        this.nomeFarmacoFilter.setOnKeyTyped(filtra -> this.filterBy());
        this.dataConsegnaFilter.setOnAction(filtra -> this.filterBy());

    }

    private void filterBy(){
        String nome = this.nomeFarmacoFilter.getText();
        LocalDate data = this.dataConsegnaFilter.getValue();
        this.listaOrdini.setItems(FXCollections.observableList(this.ol.stream().filter(recordOrdine -> {
            if(!nome.isBlank()){
                if(!recordOrdine.getNome_farmaco().toLowerCase().contains(nome.toLowerCase())) return false;
            }
            if(data != null){
                if(!recordOrdine.getData_consegna().equals(data)) return false;
            }
            return true;
        }).toList()));
    }

    public void aggiornaOrdini(ArrayList<RecordOrdine> vo) {
        ol = FXCollections.observableArrayList(vo);
        listaOrdini.setItems(ol);
    }

    /*@FXML
    public void cliccaCarica(int id_ordine) {
        new GestoreCaricoMerci(id_ordine);
    }*/

    @FXML
    public void cliccaModifica() {
    }
}
