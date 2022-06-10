package com.ogc.pharmagcode.GestioneAzienda.Interface;

import com.ogc.pharmagcode.GestioneAzienda.Control.GestoreModificaProduzione;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class InterfacciaModificaProduzione {

    GestoreModificaProduzione control;

    @FXML
    private Button confermaModificaBtn;
    @FXML
    private TextField farmaco;
    @FXML
    private TextField quantita;

    public InterfacciaModificaProduzione(GestoreModificaProduzione control){
        this.control = control;
    }



    public void initialize(){
        this.quantita.setTextFormatter(new TextFormatter<>(Utils.integerFilter));
    }

    public void conferma(ActionEvent actionEvent) {
        this.confermaModificaBtn.setDisable(true);
        try{
            String nome_farmaco = this.farmaco.getText();
            int quantita =  Integer.parseInt(this.quantita.getText());
//            GestoreModificaProduzione.log.debug("Confermato con " + nome_farmaco + " " + quantita);
            control.modificaProduzione(nome_farmaco, quantita);
        }
        finally {
            // facciamo che puliamo i campi comunque vada
            this.confermaModificaBtn.setDisable(false);
            this.farmaco.clear();
            this.quantita.clear();
        }
    }
}
