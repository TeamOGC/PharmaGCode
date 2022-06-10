package com.ogc.pharmagcode.GestioneFarmaci.Interface;

import com.ogc.pharmagcode.GestioneFarmaci.Control.GestoreCaricoMerci;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaCaricoMerci {

    private GestoreCaricoMerci gestoreCaricoMerci;

    @FXML
    private TextField lotto;

    @FXML
    private TextField quantita;

    @FXML
    public void initialize(){
        this.lotto.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public InterfacciaCaricoMerci(GestoreCaricoMerci gestoreCaricoMerci){
        this.gestoreCaricoMerci = gestoreCaricoMerci;
    }

    public void conferma(ActionEvent actionEvent) {
        int int_lotto, int_quantita;

        try {
            int_quantita = Integer.parseInt(quantita.getText());
            int_lotto =  Integer.parseInt(lotto.getText());
        }
        catch(NumberFormatException e){
            Main.log.warn("Si è tentati di convertire una stringa in un numero ed è finita male..", e);
            return;
        }
        this.gestoreCaricoMerci.caricaFarmaco(int_lotto, int_quantita);
        Utils.creaPannelloConferma("Sono stati correttamente caricati "+quantita.getText()+" del lotto "+lotto.getText());
    }
}
