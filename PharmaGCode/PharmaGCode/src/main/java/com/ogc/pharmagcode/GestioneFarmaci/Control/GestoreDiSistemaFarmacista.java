package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.ogc.pharmagcode.Utils.DBMSDaemon;

public class GestoreDiSistemaFarmacista implements Serializable {
    private int giornoUltimaChiamata = 0;
    private HashMap<Integer, Integer> merceCaricata;
    private Ordine[] ordini;

    public GestoreDiSistemaFarmacista() {

    }

    public void chiediOrario() {
        int h = Main.orologio.chiediOrario().getHour();

        if (h == 20 && giornoUltimaChiamata != Main.orologio.chiediOrario().getDayOfMonth()) {
            OrdinePeriodico[] ordiniPeriodici;
            if (!confrontaOrdiniMerce()) {
                Utils.cambiaInterfaccia("GestioneFarmaci/AvvisoMancatoCaricamento.fxml", new Stage(), 600, 400);
            }
            do {
                ordiniPeriodici = DBMSDaemon.queryOrdiniPeriodici(Main.idFarmacia); // TODO: Serve una conferma: Qua voglio tutti gli ordini periodici di una farmacia in una qualsiasi data, giusto?
            }while(!DBMSDaemon.queryCreaOrdini(ordiniPeriodici) || ordiniPeriodici==null); //Finche le query non vengono eseguite correttamente prova a rifarle
            giornoUltimaChiamata = Main.orologio.chiediOrario().getDayOfMonth();
            serializza();
            //DBMSDaemon.queryOrdiniDiUnaFarmaciaUnaData(Main.idFarmacia,Main.orologio.chiediOrario().toLocalDate());
        }
        if (Main.orologio.confrontaTimer()) {
            //Invia segnalazione all'azienda
            if (!confrontaOrdiniMerce()) {
                aggiornaStatoOrdini();
            }
            System.out.println("Mancato caricamento");
        }

    }

    public boolean confrontaOrdiniMerce() {
        merceCaricata = DBMSDaemon.queryMerceCaricata(Main.idFarmacia, Main.orologio.chiediOrario().toLocalDate());
        ordini = DBMSDaemon.queryOrdini(Main.idFarmacia, Main.orologio.chiediOrario().toLocalDate());
        for (Ordine o : ordini) {
            Integer qtyOrdine = merceCaricata.get(o.getId_ordine());
            if (qtyOrdine == null)
                return false;
            else if (qtyOrdine < o.getQuantita())
                return false;
        }
        return true;
    }

    public void aggiornaStatoOrdini() {
        for (Ordine o : ordini) {
            Integer qtyOrdine = merceCaricata.get(o.getId_ordine());
            if (qtyOrdine == null || qtyOrdine < o.getQuantita()) {
                o.setStato("Da Verificare");
                DBMSDaemon.queryAggiornaStatoOrdine(o);
            }
        }
    }

    public void creaOrdiniPeriodici() { //TODO Manca l'implementazione del caso d'uso Ordine Periodico
        DBMSDaemon.queryCreaOrdini(getOrdiniPeriodiciFarmacia(Main.idFarmacia));
    }

    private OrdinePeriodico[] getOrdiniPeriodici() {
        return DBMSDaemon.queryOrdiniPeriodici();
    }


    private OrdinePeriodico[] getOrdiniPeriodiciFarmacia(int idFarmacia) {
        int giornoSettimana = Main.orologio.chiediOrario().getDayOfWeek().getValue();
        OrdinePeriodico[] temp = DBMSDaemon.queryOrdiniPeriodici(Main.idFarmacia, giornoSettimana);
        List<OrdinePeriodico> ordinePeriodicoList = new ArrayList<>();
        ordinePeriodicoList.addAll(Arrays.asList(temp));
        return ordinePeriodicoList.toArray(new OrdinePeriodico[0]);
    }

    public void serializza() {
        try {
            FileOutputStream fout = new FileOutputStream("gds.time");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("File non esistente");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
