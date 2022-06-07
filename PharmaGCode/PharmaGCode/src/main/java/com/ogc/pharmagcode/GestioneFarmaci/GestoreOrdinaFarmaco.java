package com.ogc.pharmagcode.GestioneFarmaci;

import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreOrdinaFarmaco {
    InterfacciaOrdinaFarmaco i;
    Stage s;
    Farmaco f;
    public GestoreOrdinaFarmaco(Farmaco f){
        s=new Stage();
        this.f=f;
        i=(InterfacciaOrdinaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/OrdinaFarmaco.fxml",s,c->{return new InterfacciaOrdinaFarmaco(f,this);});
    }

    public void creaOrdine(int quantita, LocalDate d,boolean accettaInScadenza){
        Ordine o=new Ordine(-1,
                f.getId_farmaco(),
                f.getNome(), Main.idFarmacia, d, "In Lavorazione",quantita);
        int q=DBMSDaemon.queryCreaOrdineTemp(o, accettaInScadenza);
        if(q==0){
            Utils.creaPannelloConferma("Ordine Creato Correttamente");
            s.close();
        }else if(q>0){
            Stage s=new Stage();
            Utils.cambiaInterfaccia("GestioneFarmaci/ModalitaConsegnaPopup.fxml", s, c-> {
                return new PannelloAvvisoDisponibilita(stessaData->{ordinaStessaData(o,accettaInScadenza,quantita);
                                                                    s.close();},
                                                        dateDiverse->{ordinaDateSeparate(o,accettaInScadenza,quantita,q);
                                                                        s.close();});
            });
        }
    }

    private void ordinaStessaData(Ordine o, boolean accettaInScadenza, int quantita){
        o.setQuantita(quantita);
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", accettaInScadenza);
    }

    private void ordinaDateSeparate(Ordine o, boolean accettaInScadenza, int quantita, int q){
        o.setQuantita(quantita-q);
        if(DBMSDaemon.queryCreaOrdineTemp(o,accettaInScadenza)>0){
            Utils.creaPannelloErrore("C'è stato un errore con l'ordine");
        }
        o.setQuantita(q);
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", accettaInScadenza);
    }



}
