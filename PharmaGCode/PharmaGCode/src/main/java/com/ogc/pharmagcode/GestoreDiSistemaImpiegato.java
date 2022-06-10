package com.ogc.pharmagcode;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.beans.DefaultPersistenceDelegate;
import java.beans.EventHandler;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class GestoreDiSistemaImpiegato {
    private int giornoUltimaChiamata=0;

    public void chiediData(){
        int h=Main.orologio.chiediOrario().getHour();

        if(Main.sistema==0){
            if(h==20  && giornoUltimaChiamata!=Main.orologio.chiediOrario().getDayOfWeek().getValue()){

                giornoUltimaChiamata = Main.orologio.chiediOrario().getDayOfWeek().getValue();
                serializza();
                aggiornaScorte();
                evadiOrdiniInAttesa();
            }
            if(Main.orologio.confrontaTimer()){
                //Invia segnalazione all'azienda
                System.out.println("Mancato caricamento");
            }
        }
    }
    private void aggiornaScorte(){
        DBMSDaemon.queryAggiornaScorte(Main.orologio.chiediOrario().toLocalDate());
        Utils.creaPannelloConferma("Scorte Aggiornate Correttamente");
    }
    public void serializza(){
        try {
            FileOutputStream fout=new FileOutputStream("gds.time");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File non esistente");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void evadiOrdiniInAttesa(){
        OrdinePeriodico[] ordiniPeriodici=DBMSDaemon.queryOrdiniPeriodici();
        Ordine[] ordiniInAttesa=DBMSDaemon.queryOrdiniInAttesa();
        for(Ordine o:ordiniInAttesa){
            evadiOrdineInAttesa(o,ordiniPeriodici);
        }
    }

    private void evadiOrdineInAttesa(Ordine o, OrdinePeriodico[] ordiniPeriodici){
        int totaleFarmaco=DBMSDaemon.queryQuantitaFarmaco(o.getId_farmaco());
        for(OrdinePeriodico op:ordiniPeriodici){
            if(op.getId_farmaco()==o.getId_farmaco()){
                totaleFarmaco-=op.getQuantita();
            }
            if(totaleFarmaco>0){
                o.setStato("In Lavorazione");
                DBMSDaemon.queryAggiornaData(o, Main.orologio.chiediOrario().toLocalDate().plusDays(2));
                int qty=Math.min(o.getQuantita(),totaleFarmaco);
                DBMSDaemon.queryAggiornaQuantitaOrdine(o, qty);
                DBMSDaemon.queryAggiornaStatoOrdine(o);
                //Ordine temp=new Ordine(-1,o.getId_farmaco(),o.getNome_farmaco(),o.getId_farmacia(),Main.orologio.chiediOrario().toLocalDate().plusDays(2),"In Lavorazione",qty);
                //DBMSDaemon.queryCreaOrdine(temp,"In Lavorazione",false);
                if(o.getQuantita()>qty) {
                    Ordine temp2=new Ordine(-1,o.getId_farmaco(),o.getNome_farmaco(),o.getId_farmacia(),o.getData_consegna(),"In Attesa Di Disponibilita",o.getQuantita()-qty);
                    DBMSDaemon.queryCreaOrdine(temp2, "In Attesa Di Disponibilita",false);
                }
            }
        }
    }
}
