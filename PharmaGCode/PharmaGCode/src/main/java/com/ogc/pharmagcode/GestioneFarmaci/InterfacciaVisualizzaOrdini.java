package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.RecordOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class InterfacciaVisualizzaOrdini {
    @FXML
    private TableView<RecordOrdine> listaOrdini;
    GestoreVisualizzaOrdini gestoreVisualizzaOrdini;

    ObservableList<RecordOrdine> ol;
    public InterfacciaVisualizzaOrdini(GestoreVisualizzaOrdini gestoreVisualizzaOrdini){
        this.gestoreVisualizzaOrdini=gestoreVisualizzaOrdini;
    }
    @FXML
    protected void initialize(){
        ol=FXCollections.observableArrayList( gestoreVisualizzaOrdini.chiediOrdini());
        listaOrdini.setItems(ol);
    }
    public void aggiornaOrdini(ArrayList<RecordOrdine> vo){
        ol=FXCollections.observableArrayList(vo);
        listaOrdini.setItems(ol);
    }
    @FXML
    public void cliccaCarica(int id_ordine){ new GestoreCaricoMerci( id_ordine); }

    @FXML
    public void cliccaModifica(){ }
}
