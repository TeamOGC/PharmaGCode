package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.InterfacciaPrincipale;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleFarmacista extends InterfacciaPrincipale {

    public InterfacciaPrincipaleFarmacista(String nome, String cognome){
        super(nome + " "+cognome+" "+" Farmacia: "+ Main.idFarmacia);
    }
    @FXML
    protected void cliccaCercaFarmaco(){
        new GestoreCercaFarmaco();
    }
    @FXML
    protected void cliccaVisualizzaOrdini(){

        new GestoreVisualizzaOrdini();
    }
    @FXML
    protected void cliccaCaricoMerci(){

    }
    @FXML
    protected void cliccaScaricoMerci(){

    }

}
