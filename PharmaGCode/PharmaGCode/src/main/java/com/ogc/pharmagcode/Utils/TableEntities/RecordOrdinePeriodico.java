package com.ogc.pharmagcode.Utils.TableEntities;

import com.ogc.pharmagcode.Entity.OrdinePeriodico;
import com.ogc.pharmagcode.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.time.LocalDate;

public class RecordOrdinePeriodico extends OrdinePeriodico {

    private LocalDate nextConsegna = null;
    private String nomeBottone =  null;
    private EventHandler<ActionEvent> callback = null;
    private Button bottone = null;

    public RecordOrdinePeriodico(int id_farmacia, int id_farmaco, int quantita, int periodicita, String nomeFarmaco, String nomeFarmacia, String nomeBottone, EventHandler<ActionEvent> callback) {
        super(id_farmacia, id_farmaco, quantita, periodicita, nomeFarmaco, nomeFarmacia);
        this.nomeBottone = nomeBottone;
        this.callback = callback;
        this.nextConsegna = Main.orologio.chiediOrario().toLocalDate();
        calcolaNextConsegna();
        initializeButton();
    }

    public Button getBottone() {
        return bottone;
    }

    private void calcolaNextConsegna(){
        int weekDay = this.nextConsegna.getDayOfWeek().getValue();
        int diff = 0;
        if(this.getPeriodicita() < weekDay){
            // la prossima consegna è la prossima settimana
            diff += 7;
        }
        diff += getPeriodicita()-weekDay;
        this.nextConsegna = this.nextConsegna.plusDays(diff);
    }

    public LocalDate getNextConsegna(){
        return this.nextConsegna;
    }

    private void initializeButton(){
        if(nomeBottone != null && !nomeBottone.isBlank()) {
            bottone = new Button(nomeBottone);
            bottone.setOnAction(callback);
            bottone.getStyleClass().add("btn");
            bottone.setMaxHeight(10);
        }
    }

    public static RecordOrdinePeriodico fromOrdinePeriodico(OrdinePeriodico ordinePeriodico, String nomeBottone, EventHandler<ActionEvent> callback){
        return new RecordOrdinePeriodico(
                ordinePeriodico.getId_farmacia(),
                ordinePeriodico.getId_farmaco(),
                ordinePeriodico.getQuantita(),
                ordinePeriodico.getPeriodicita(),
                ordinePeriodico.getNomeFarmaco(),
                ordinePeriodico.getNomeFarmacia(),
                nomeBottone,
                callback
        );
    }
    public static RecordOrdinePeriodico fromOrdinePeriodico(OrdinePeriodico ordinePeriodico){
        return new RecordOrdinePeriodico(
                ordinePeriodico.getId_farmacia(),
                ordinePeriodico.getId_farmaco(),
                ordinePeriodico.getQuantita(),
                ordinePeriodico.getPeriodicita(),
                ordinePeriodico.getNomeFarmaco(),
                ordinePeriodico.getNomeFarmacia(),
                null,
                null
        );
    }
}
