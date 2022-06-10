package com.ogc.pharmagcode.GestioneConsegna;

import com.ogc.pharmagcode.Entity.Collo;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

public class GestoreFirmaConsegne {
    public Collo daFirmare;
    private GestoreVisualizzaConsegne gestoreVisualizzaConsegne;
    private InterfacciaFirmaConsegne boundary;
    public GestoreFirmaConsegne(Stage s, GestoreVisualizzaConsegne gestoreVisualizzaConsegne,  Collo colloDaFirmare){
        this.daFirmare = colloDaFirmare;
        this.gestoreVisualizzaConsegne = gestoreVisualizzaConsegne;
        boundary = (InterfacciaFirmaConsegne) Utils.cambiaInterfaccia("GestioneConsegna/FirmaConsegne.fxml", s, c->{ return new InterfacciaFirmaConsegne(this);});
    }

    public void firmaCollo(String firma){
        Main.log.info("Sto firmando il collo " + daFirmare.getId_collo() + " " + firma);
        DBMSDaemon.queryFirmaCollo(firma, daFirmare);
        gestoreVisualizzaConsegne.chiediConsegne();
        ((Stage) this.boundary.listaOrdini.getScene().getWindow()).close();
    }
}
