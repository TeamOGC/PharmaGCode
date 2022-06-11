package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ogc.pharmagcode.Utils.DBMSDaemon.queryOrdiniPeriodici;

public class GestoreDiSistemaFarmacista implements Serializable {
    private int giornoUltimaChiamata = 0;
    private HashMap<Integer, Integer> merceCaricata;
    private Ordine[] ordini;

    public GestoreDiSistemaFarmacista() {

    }

    public void chiediOrario() {
        int h = Main.orologio.chiediOrario().getHour();

        if (Main.sistema == 0) {
            if (h == 20 && giornoUltimaChiamata != Main.orologio.chiediOrario().getDayOfMonth()) {
                if (!confrontaOrdiniMerce()) {
                    Utils.cambiaInterfaccia("GestioneFarmaci/AvvisoMancatoCaricamento.fxml", new Stage(), 600, 400);
                    OrdinePeriodico[] ordiniPeriodici;
                    do {
                        ordiniPeriodici = DBMSDaemon.queryOrdiniPeriodici(Main.idFarmacia);
                    }while(!DBMSDaemon.queryCreaOrdini(ordiniPeriodici) || ordiniPeriodici==null); //Finche le query non vengono eseguite correttamente prova a rifarle
                    giornoUltimaChiamata = Main.orologio.chiediOrario().getDayOfMonth();
                }
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
        return queryOrdiniPeriodici();
    }


    private OrdinePeriodico[] getOrdiniPeriodiciFarmacia(int idFarmacia) {
        OrdinePeriodico[] temp = DBMSDaemon.queryOrdiniPeriodici(Main.idFarmacia);
        int giornoSettimana = Main.orologio.chiediOrario().getDayOfWeek().getValue();
        List<OrdinePeriodico> ordinePeriodicoList = new ArrayList<>();
        for (OrdinePeriodico o : temp) {
            if (o.getPeriodicita() == giornoSettimana) {
                ordinePeriodicoList.add(o);
            }
        }
        return ordinePeriodicoList.toArray(OrdinePeriodico[]::new);
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
