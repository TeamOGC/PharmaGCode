package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.InterfacciaPrincipale;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InterfacciaPrincipaleFarmacista extends InterfacciaPrincipale {
    public InterfacciaPrincipaleFarmacista(String nome, String cognome, String idFarmacia){
        super(nome + " "+cognome+" "+" Farmacia: "+idFarmacia);
    }

    @FXML
    protected void cliccaCercaFarmaco(){

    }
    @FXML
    protected void cliccaVisualizzaOrdini(){
        new GestoreVisualizzaOrdini((Stage) nomeUtente.getScene().getWindow());
    }
    @FXML
    protected void cliccaCaricoMerci(){

    }
    @FXML
    protected void cliccaScaricoMerci(){

    }

}
