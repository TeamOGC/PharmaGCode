package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.RecordLista;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestoreVisualizzaOrdini {
    private InterfacciaVisualizzaOrdini i;
    private ArrayList<RecordLista> listaOrdini=new ArrayList<>();
    private LocalDate d;
    public GestoreVisualizzaOrdini(){
        d=Main.orologio.chiediOrario().toLocalDate();
        i=(InterfacciaVisualizzaOrdini) Utils.cambiaInterfaccia("GestioneFarmaci/VisualizzaOrdiniFarmacia.fxml",
                new Stage(),
                c->{return new InterfacciaVisualizzaOrdini(this);});
    }

    public ArrayList<RecordLista> chiediOrdini(){
        Ordine[] ordini=DBMSDaemon.queryVisualizzaOrdiniFarmacia(Main.idFarmacia);
        for(Ordine o:ordini){
            EventHandler<ActionEvent> e=n->{};
            String bottone="";
            if(Duration.between(d.atTime(0,0,1), o.getData_consegna().atTime(0,0,1)).toDays()>1){
                e=modifica->{new GestoreModificaOrdine(o);};
                bottone="Modifica";
            }else if(d.equals(o.getData_consegna())){
                e=carica->{new GestoreCaricoMerci(o.getId_ordine());};
                bottone="Carica";
            }
            listaOrdini.add(new RecordLista(e,984,
                            o.getNome_farmaco()+"",
                            o.getStato(),o.getQuantita()+"",
                            o.getData_consegna().format(DateTimeFormatter.ISO_LOCAL_DATE),
                            bottone));

        }
        return listaOrdini;
    }
}
