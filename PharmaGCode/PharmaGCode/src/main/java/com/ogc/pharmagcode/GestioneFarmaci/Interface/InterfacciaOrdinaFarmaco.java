package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreOrdinaFarmaco;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InterfacciaOrdinaFarmaco {
    Farmaco f;
    @FXML
    private Label nomeFarmaco, princAttivo;
    @FXML
    private TextField quantita;
    @FXML
    private DatePicker dataDiConsegna;
    @FXML
    private CheckBox checkboxScadenza;

    private GestoreOrdinaFarmaco gof;

    public InterfacciaOrdinaFarmaco(Farmaco f, GestoreOrdinaFarmaco gof) {
        this.gof = gof;
        this.f = f;
    }

    @FXML
    protected void initialize() {
        nomeFarmaco.setText(f.getNome());
        princAttivo.setText(f.getPrincipio_attivo());
    }

    @FXML
    protected void conferma() {
        gof.creaOrdine(Integer.parseInt(quantita.getText()), dataDiConsegna.getValue(), checkboxScadenza.isSelected());
    }
}
