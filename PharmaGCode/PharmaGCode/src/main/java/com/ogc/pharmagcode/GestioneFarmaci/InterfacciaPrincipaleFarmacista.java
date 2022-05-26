package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.InterfacciaPrincipale;
import javafx.fxml.FXML;

public class InterfacciaPrincipaleFarmacista extends InterfacciaPrincipale {
    public InterfacciaPrincipaleFarmacista(String nome, String cognome, String idFarmacia){
        super(nome + " "+cognome+" "+" Farmacia: "+idFarmacia);
    }

    @FXML
    protected void cliccaCercaFarmaco(){

    }
    @FXML
    protected void cliccaVisualizzaOrdini(){

    }
    @FXML
    protected void cliccaCaricoMerci(){

    }
    @FXML
    protected void cliccaScaricoMerci(){

    }

}
