package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreOrdinaFarmaco;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private final GestoreOrdinaFarmaco gof;

    public InterfacciaOrdinaFarmaco(Farmaco f, GestoreOrdinaFarmaco gof) {
        this.gof = gof;
        this.f = f;
    }

    @FXML
    protected void initialize() {
        quantita.setTextFormatter(new TextFormatter<>(Utils.nonZeroPositiveIntegerFilter));
        nomeFarmaco.setText(f.getNome());
        princAttivo.setText(f.getPrincipio_attivo());
        dataDiConsegna.setValue(Main.orologio.chiediOrario().toLocalDate().plusDays(3));
    }

    @FXML
    protected void conferma() {
        if(quantita.getText().isBlank() || dataDiConsegna.getValue().isBefore(Main.orologio.chiediOrario().toLocalDate().plusDays(3))){
            Utils.creaPannelloErrore("Inserisci dati validi");
            return;
        }
        gof.creaOrdine(Integer.parseInt(quantita.getText()), dataDiConsegna.getValue(), checkboxScadenza.isSelected());
    }
}
