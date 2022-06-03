package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Utils.RecordLista;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InterfacciaCercaFarmaco {
    private GestoreCercaFarmaco gestoreCercaFarmaco;
    @FXML
    private TextField nomeFarmaco;
    @FXML
    private TextField princAttivo;
    @FXML
    private ListView<RecordLista> listaFarmaci;
    public InterfacciaCercaFarmaco(GestoreCercaFarmaco gestoreCercaFarmaco){
        this.gestoreCercaFarmaco=gestoreCercaFarmaco;
    }
    @FXML
    protected void initialize(){
        listaFarmaci.setFixedCellSize(55);
    }
    @FXML
    protected void conferma(){
        gestoreCercaFarmaco.cercaFarmaci(nomeFarmaco.getText(),princAttivo.getText());
    }

    public void aggiornaFarmaci(ObservableList<RecordLista> ol){
        listaFarmaci.setItems(ol);
    }
}
