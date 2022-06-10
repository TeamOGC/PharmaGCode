package com.ogc.pharmagcode.GestioneFarmaci.Control;

import com.ogc.pharmagcode.Entity.Farmaco;
import com.ogc.pharmagcode.Entity.Ordine;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.InterfacciaOrdinaFarmaco;
import com.ogc.pharmagcode.GestioneFarmaci.Interface.PannelloAvvisoDisponibilita;
import com.ogc.pharmagcode.Main;
import com.ogc.pharmagcode.Utils.DBMSDaemon;
import com.ogc.pharmagcode.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreOrdinaFarmaco {
    InterfacciaOrdinaFarmaco i;
    Stage s;
    Farmaco f;

    public GestoreOrdinaFarmaco(Farmaco f) {
        s = new Stage();
        this.f = f;
        i = (InterfacciaOrdinaFarmaco) Utils.cambiaInterfaccia("GestioneFarmaci/OrdinaFarmaco.fxml", s, c -> {
            return new InterfacciaOrdinaFarmaco(f, this);
        });
    }

    public void creaOrdine(int quantita, LocalDate d, boolean accettaInScadenza) {
        Ordine o = new Ordine(-1,
                f.getId_farmaco(),
                f.getNome(), Main.idFarmacia, d, "In Lavorazione", quantita);
        int quantitaEccedente = DBMSDaemon.queryCreaOrdineTemp(o, accettaInScadenza);
        if (quantitaEccedente == 0) {
            Utils.creaPannelloConferma("Ordine Creato Correttamente");
            s.close();
        } else if (quantitaEccedente > 0) {
            Stage s = new Stage();
            Utils.cambiaInterfaccia("GestioneFarmaci/ModalitaConsegnaPopup.fxml", s, c -> {
                return new PannelloAvvisoDisponibilita(stessaData -> {
                    ordinaStessaData(o, accettaInScadenza, quantita);
                    s.close();
                },
                        dateDiverse -> {
                            ordinaDateSeparate(o, accettaInScadenza, quantita, quantitaEccedente);
                            s.close();
                        });
            });
        }
    }

    /**
     * Consente di ordinare tutta la quantita non disponibile per
     *
     * @param o
     * @param accettaInScadenza
     * @param quantita
     */
    private void ordinaStessaData(Ordine o, boolean accettaInScadenza, int quantita) {
        o.setQuantita(quantita);
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", false);
    }

    /**
     * Consente di dividere l'ordine in due ordini separati, uno con le quantita attualmente ordinabili e uno in attesa di disponibilità
     *
     * @param o
     * @param accettaInScadenza
     * @param quantita
     * @param quantitaEccedente
     */
    private void ordinaDateSeparate(Ordine o, boolean accettaInScadenza, int quantita, int quantitaEccedente) {
        o.setQuantita(quantita - quantitaEccedente);
        DBMSDaemon.queryCreaOrdine(o, "In Lavorazione", accettaInScadenza);
        o.setQuantita(quantitaEccedente);
        DBMSDaemon.queryCreaOrdine(o, "In Attesa Di Disponibilita", false);
    }


}