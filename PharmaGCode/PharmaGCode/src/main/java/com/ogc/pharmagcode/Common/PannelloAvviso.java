package com.ogc.pharmagcode.Common;

import com.ogc.pharmagcode.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PannelloAvviso {

    public Button exitBtn;
    public Button confirmBtn;

    private final EventHandler<ActionEvent> onConfirm;
    private final EventHandler<ActionEvent> onExit;

    public PannelloAvviso(EventHandler<ActionEvent> onConfirm, EventHandler<ActionEvent> onExit){
        this.onConfirm = onConfirm;
        this.onExit = onExit;
    }

    public void initialize(){
        this.confirmBtn.addEventHandler(ActionEvent.ACTION, chiudi -> ((Stage) this.exitBtn.getScene().getWindow()).close());
        this.exitBtn.addEventHandler(ActionEvent.ACTION, chiudi -> ((Stage) this.exitBtn.getScene().getWindow()).close());
        this.confirmBtn.addEventHandler(ActionEvent.ACTION, onConfirm);
        this.exitBtn.addEventHandler(ActionEvent.ACTION, onExit);
        }

}
